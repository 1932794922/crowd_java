package xiaozaiyi.crowd.service.imp;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.feign.IRedisClientFeign;
import xiaozaiyi.crowd.service.ReturnService;
import xiaozaiyi.crowd.util.CustomUtils;
import xiaozaiyi.crowd.util.QOssUploadUtil;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.ProjectVO;
import xiaozaiyi.crowd.vo.ReturnVO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-02   21:37
 */
@Service
public class ReturnServiceImpl implements ReturnService {


    @Autowired
    private IRedisClientFeign iRedisClientFeign;

    @Override
    public R<ReturnVO> fileUpload(List<MultipartFile> file) {
        if (file.isEmpty()) {
            return R.fail("文件不能为空");
        }
        ReturnVO returnVO;
        // 1.判断文件是单文件还是多文件
        if (file.size() == 1) {
            // 2.获取文件
            String resourceUrl = QOssUploadUtil.uploadResource(file.get(0));
            // 3.封装返回对象
            returnVO = new ReturnVO();
            // headerPicturePath 头图的路径
            returnVO.setDescribePicPath(resourceUrl);
        } else {
            // 3.获取多文件
            return R.fail(CustomConstant.ONLY_ONE_IMAGES);

        }
        return R.data(returnVO);
    }

    @Override
    public R<ReturnVO> saveReturn(List<ReturnVO> returnVO, HttpServletRequest request) {

        // 1.获取当前登录人的id
        String id = CustomUtils.getJwt2Value(request);
        // 2.获取 redis中的 ProjectVO 数据
        // 格式 project::33
        String redisKey = CustomConstant.TEMP_PROJECT_PREFIX + id;
        R<String> stringR = iRedisClientFeign.getRedisValueByKey(redisKey);
        boolean success = stringR.isSuccess();
        // 检查 redis中是否是有效数据
        if (!success) {
            return R.status(false);
        }
        ProjectVO projectVO = JSON.parseObject(stringR.getData(), ProjectVO.class);
        // 3.存入 projectVO 中获取回报信息的集合
        List<ReturnVO> returnProjectVOList = projectVO.getReturnVOList();
        // 4.判断集合是否有效
        if (returnProjectVOList == null || returnProjectVOList.size() == 0) {
            returnProjectVOList = new ArrayList<>();
            // 设置到 projectVO 中
            projectVO.setReturnVOList(returnProjectVOList);
        }
        // 清空原来的集合
        returnProjectVOList.clear();

        // 5.存到ProjectVO集合中
        returnProjectVOList.addAll(returnVO);
        // 6.在次讲  projectVO 存入redis
        String projectVOStr = JSON.toJSONString(projectVO);
        R<String> saveRedisResultData = iRedisClientFeign.setRedisKeyValueWithTimeout(redisKey, projectVOStr, CustomConstant.EXPIRED_TIME, TimeUnit.MINUTES);
        boolean saveRedisResult = saveRedisResultData.isSuccess();
        return R.status(saveRedisResult);
    }
}
