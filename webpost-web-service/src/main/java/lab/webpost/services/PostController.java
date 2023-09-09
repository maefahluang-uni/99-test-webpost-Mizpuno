package lab.webpost.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lab.webpost.domain.Post;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    // TODO: get all Posts
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postRepository.findAll();
        return ResponseEntity.ok(posts);
    }

    //TODO: getting post by id
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        // TODO: check if post is null
        Optional<Post> postOpt = postRepository.findById(id);

        if (!postOpt.isPresent()) {
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }

        Post post = postOpt.get();
        return ResponseEntity.ok(post);
    }

    //TODO: find by title
    @GetMapping("/posts/title/{title}")
    public ResponseEntity<List<Post>> getPostByTitle(@PathVariable String title) {
        List<Post> post = postRepository.findByTitle(title);

        if (post.isEmpty()) {
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(post);
    }

    // TODO: adding new post
    @PostMapping("/posts")
    public ResponseEntity<String> addPost(@RequestBody Post post) {
        postRepository.save(post);
        return ResponseEntity.status(201).body("Post created.");
    }

    // TODO: delete post by id
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        Optional<Post> postOpt = postRepository.findById(id);

        if (!postOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not found.");
        }

        postRepository.deleteById(id);
        return ResponseEntity.status(204).body("Post deleted.");
    }

    //TODO: delete all posts
    @DeleteMapping("/posts")
    public ResponseEntity<String> deleteAllPosts() {
        postRepository.deleteAll();
        return ResponseEntity.status(204).body("Truncated all.");
    }

}
