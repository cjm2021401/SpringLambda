package WhaTap.LambdaTestApplication.Repository;

import WhaTap.LambdaTestApplication.Domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User member);
    Optional<User> findByEmail(String Email);
    void updateCount(String Email, int Count);
}
