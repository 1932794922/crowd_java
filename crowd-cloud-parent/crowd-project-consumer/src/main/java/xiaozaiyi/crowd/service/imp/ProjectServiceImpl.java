package xiaozaiyi.crowd.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xiaozaiyi.crowd.service.ProjectService;
import xiaozaiyi.crowd.util.QOssUploadUtil;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.ProjectVO;

import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-02   11:57
 */
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    @Override
    public R<ProjectVO> fileUpload(List<MultipartFile> file) {
        if (file.isEmpty()) {
            return R.fail("文件不能为空");
        }
        ProjectVO projectVO;
        // 1.判断文件是单文件还是多文件
        if (file.size() == 1) {
            // 2.获取文件
            String resourceUrl = QOssUploadUtil.uploadResource(file.get(0));
            // 3.封装返回对象
             projectVO = new ProjectVO();
            // headerPicturePath 头图的路径
            projectVO.setHeaderPicturePath(resourceUrl);
        } else {
            // 3.获取多文件
            List<String> resourceUrlList = QOssUploadUtil.uploadResource(file);
            // 4.封装返回对象
             projectVO = new ProjectVO();
            // detailPicturePathList 详情图的路径
            projectVO.setDetailPicturePathList(resourceUrlList);

        }
        return R.data(projectVO);
    }
}
