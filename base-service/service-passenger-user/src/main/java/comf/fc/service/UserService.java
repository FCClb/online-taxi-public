package comf.fc.service;

import com.fc.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("UserService 被调用，手机号passengerPhone=" + passengerPhone);

        //todo 根据手机号查询用户信息

        //todo 判断用户信息是否存在

        //todo 如果不存在，则插入用户信息

        return ResponseResult.success(passengerPhone);
    }
}
