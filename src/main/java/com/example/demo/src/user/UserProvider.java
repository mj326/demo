package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.GetUserFeedRes;
import com.example.demo.src.user.model.GetUserInfoRes;
import com.example.demo.src.user.model.GetUserPostsRes;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.USERS_EMPTY_USER_ID;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }





    /* userIdxByJwt = 로그인된 정보, userIdx = Pathvariable로 받은 값

     */
    public GetUserFeedRes retrieveUserFeed(int userIdxByJwt, int userIdx) throws BaseException {
        Boolean isMyFeed = true;

        if (checkUserExist(userIdx) == 0) {
            throw new BaseException(USERS_EMPTY_USER_ID);

        }


        try {
            if (userIdxByJwt != userIdx)
                isMyFeed = false;

            GetUserInfoRes getUserInfo = userDao.selectUserInfo(userIdx);
            List<GetUserPostsRes> getUserPosts = userDao.selectUserPosts(userIdx);
            GetUserFeedRes getUsersRes = new GetUserFeedRes(isMyFeed, getUserInfo, getUserPosts);
            return getUsersRes;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }




    public GetUserRes getUsersByIdx(int userIdx) throws BaseException{
        try{
            GetUserRes getUserRes = userDao.getUsersByIdx(userIdx);
            return getUserRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }



    public int checkUserExist(int userIdx) throws BaseException{
        try{
            return userDao.checkUserExist(userIdx);
        } catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public int checkEmail(String Email) throws BaseException{
        try{
            return userDao.checkEmail(Email);
        } catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
