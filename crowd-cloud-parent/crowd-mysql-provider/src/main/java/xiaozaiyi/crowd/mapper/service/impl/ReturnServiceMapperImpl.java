package xiaozaiyi.crowd.mapper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xiaozaiyi.crowd.mapper.ReturnMapper;
import xiaozaiyi.crowd.po.ReturnPO;
import xiaozaiyi.crowd.mapper.service.ReturnMapperService;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-03   19:42
 */
@Service
@Transactional(readOnly = true)
public class ReturnServiceMapperImpl extends ServiceImpl<ReturnMapper, ReturnPO> implements ReturnMapperService {

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class,readOnly = true)
    public boolean saveOrUpdate(ReturnPO entity) {
        return super.saveOrUpdate(entity);
    }

}
