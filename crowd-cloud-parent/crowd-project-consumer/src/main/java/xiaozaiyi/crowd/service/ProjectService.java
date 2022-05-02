package xiaozaiyi.crowd.service;

import org.springframework.web.multipart.MultipartFile;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.ProjectVO;

import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-02   11:56
 */
public interface ProjectService {
    /**
     * 上传资源
     *
     * @param file
     * @return 地址列表
     */
    R<ProjectVO> fileUpload(List<MultipartFile> file);
}
