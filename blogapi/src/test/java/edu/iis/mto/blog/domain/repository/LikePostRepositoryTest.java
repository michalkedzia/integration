package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private LikePostRepository likePostRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    UserRepository userRepository;

    private BlogPost blogPost;

    @BeforeEach
    void setUp() {
        User tom = createUser("Tom", "Smith", "Tom@email.com");
        userRepository.save(tom);
        blogPost = createBlogPost("TestEntry", tom);
        blogPostRepository.save(blogPost);
    }

    @Test
    void shouldFindNoLikesIfRepositoryIsEmpty() {
        List<LikePost> likes = likePostRepository.findAll();
        assertThat(likes, hasSize(0));
    }

    @Test
    void shouldUpdatePostLikes() {
        User userWhoLikesPost = createUser("dummyValue", "dummyValue@dummyValue.pl", "dummyValue");
        userRepository.save(userWhoLikesPost);
        likePostRepository.save(createLikePost(blogPost, userWhoLikesPost));
        likePostRepository.save(createLikePost(blogPost, userWhoLikesPost));
        likePostRepository.save(createLikePost(blogPost, userWhoLikesPost));
        List<LikePost> all = likePostRepository.findAll();
        assertThat(all, hasSize(3));
    }

    @Test
    void shouldFindLikesByUserAndPost() {
        User userWhoLikesPost = createUser("dummyValue", "dummyValue@dummyValue.pl", "dummyValue");
        userRepository.save(userWhoLikesPost);
        LikePost likePost = createLikePost(blogPost, userWhoLikesPost);
        likePostRepository.save(likePost);

        Optional<LikePost> byUserAndPost = likePostRepository.findByUserAndPost(userWhoLikesPost, blogPost);
        Assertions.assertEquals(Optional.of(likePost), byUserAndPost);
    }

    LikePost createLikePost(BlogPost blogPost, User user) {
        LikePost likePost = new LikePost();
        likePost.setUser(user);
        likePost.setPost(blogPost);
        return likePost;
    }

    BlogPost createBlogPost(String entry, User user) {
        BlogPost blogPost = new BlogPost();
        blogPost.setEntry(entry);
        blogPost.setUser(user);
        return blogPost;
    }

    User createUser(String firstName, String lastName, String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAccountStatus(AccountStatus.CONFIRMED);
        return user;
    }

}
