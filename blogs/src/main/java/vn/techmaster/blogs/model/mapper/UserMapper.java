package vn.techmaster.blogs.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import vn.techmaster.blogs.model.dto.UserInfo;
import vn.techmaster.blogs.model.entity.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserInfo userToUserInfo(User user);
    User userInfoToUser(UserInfo userInfo);
}
