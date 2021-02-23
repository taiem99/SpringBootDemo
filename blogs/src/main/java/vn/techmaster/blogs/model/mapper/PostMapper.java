package vn.techmaster.blogs.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import vn.techmaster.blogs.model.dto.PostPOJO;
import vn.techmaster.blogs.model.entity.Post;
import vn.techmaster.blogs.request.PostRequest;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post postRequestToPost(PostRequest postRequest);

    @Mapping(target = "user_id", source = "post.user.id")
    PostRequest postToPostRequest(Post post);

    @Mapping(target = "user_id", source = "post.user.id")
    @Mapping(target = "userFullName", source = "post.user.fullname")
    PostPOJO postToPostPOJO(Post post);
}
