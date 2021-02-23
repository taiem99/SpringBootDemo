package vn.techmaster.blogs.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import org.hibernate.CacheMode;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import vn.techmaster.blogs.exception.PostException;
import vn.techmaster.blogs.model.entity.Comment;
import vn.techmaster.blogs.model.entity.Post;
import vn.techmaster.blogs.model.entity.Tag;
import vn.techmaster.blogs.model.entity.User;
import vn.techmaster.blogs.model.mapper.PostMapper;
import vn.techmaster.blogs.reponsitory.PostRepository;
import vn.techmaster.blogs.reponsitory.TagRepository;
import vn.techmaster.blogs.reponsitory.UserRepository;
import vn.techmaster.blogs.request.CommentRequest;
import vn.techmaster.blogs.request.PostRequest;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TagRepository tagRepo;
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Post> findAll() {
       return postRepo.findAll();
    }

    @Override
    public Page<Post> findAllPaging(int page, int pageSize) {
        return postRepo.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public List<Post> getAllPostOfUser(User user) {
        return postRepo.findAll();
    }

    @Override
    public List<Post> getAllPostsByUserId(Long user_id) {
        return postRepo.findByUserId(user_id);
    }

    @Override
    public void createNewPost(PostRequest postRequest) throws PostException {
        Optional<User> optionalUser = userRepo.findById(postRequest.getUser_id());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            Post post = PostMapper.INSTANCE.postRequestToPost(postRequest);
            user.addPost(post);
            userRepo.flush();
        } else {
            throw new PostException("don't create post");
        }

    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepo.findById(id);
    }

    @Override
    public void deletePostById(Long id) {
        postRepo.deleteById(id);
    }

    @Override
    public void updatePost(PostRequest postRequest) throws PostException {
       Optional<Post> optionalPost = postRepo.findById(postRequest.getId());
       if (optionalPost.isPresent()){
           Post post = optionalPost.get();
           post.setTitle(postRequest.getTitle());
           post.setContent(postRequest.getContent());
           post.setTags(postRequest.getTags());
           postRepo.saveAndFlush(post);
       } else {
           createNewPost(postRequest);
       }

    }

    @Override
    public void addComment(CommentRequest commentRequest, Long user_id) throws PostException {
        Optional<Post> optionalPost = postRepo.findById(commentRequest.getPost_id());
        Optional<User> optionalUser = userRepo.findById(user_id);
        if (optionalPost.isPresent() && optionalUser.isPresent()){
            Post post = optionalPost.get();
            Comment comment = new Comment(commentRequest.getContent());
            comment.setUser(optionalUser.get());
            post.addComment(comment);
            postRepo.flush();
        } else {
            throw new PostException("Post or User missing");
        }

    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepo.findAll();
    }

    @Override
    public List<Post> searchPost(String term, int limit, int offset) {
        return Search.session(em).search(Post.class).where(f -> f.match()
        .fields("title", "content")
        .matching(term)).fetchHits(offset, limit);
    }

    @Override
    public void reindexFullText() {
        SearchSession searchSession = Search.session(em);
        MassIndexer indexer = searchSession.massIndexer(Post.class).dropAndCreateSchemaOnStart(true)
            .typesToIndexInParallel(2)
            .batchSizeToLoadObjects(10)
            .idFetchSize(200)
            .threadsToLoadObjects(5)
            .cacheMode(CacheMode.IGNORE);
        indexer.start();
    }

    @Override
    @Transactional
    public void generateSampleData() {
        List<User> users = userRepo.findAll();
        List<Tag> tags = tagRepo.findAll();
        int numberOfTags = tags.size();
        int maxTagsPerPost = numberOfTags / 3;
        Lorem lorem = LoremIpsum.getInstance();
        Random random = new Random();
        int numberOfUsers = users.size();
        for (int i = 0; i < 200; i++){
            User user = users.get(random.nextInt(numberOfUsers));
            Post post = new Post(lorem.getTitle(2, 5), lorem.getParagraphs(2, 4));

            int numberOfComments = random.nextInt(numberOfUsers / 2);
            for (int j = 0; j < numberOfComments; j++){
                User commenter = users.get(random.nextInt(numberOfUsers));
                Comment comment = new Comment(lorem.getParagraphs(1, 1));
                comment.setUser(commenter);
                post.addComment(comment);
            }

            int numberTagsOfPost = Math.max(1, random.nextInt(maxTagsPerPost));
            for(int k = 0; k <numberTagsOfPost; k++){
                post.addTag(tags.get(random.nextInt(numberOfTags)));
            }

            user.addPost(post);
            postRepo.save(post);
        }
        userRepo.flush();
    }
    
}
