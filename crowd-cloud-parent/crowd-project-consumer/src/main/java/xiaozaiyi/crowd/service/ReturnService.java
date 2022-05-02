package xiaozaiyi.crowd.service;

import org.springframework.web.multipart.MultipartFile;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.ReturnVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ReturnService {
    /***
     * 上传说明图片
     *
     * @param file
     * @return
     */
    R<ReturnVO> fileUpload(List<MultipartFile> file);

    /***
     * 保存回报信息
     *
     * @param returnVO
     * @param request
     * @return
     */
    R<ReturnVO> saveReturn(List<ReturnVO> returnVO, HttpServletRequest request);

}
