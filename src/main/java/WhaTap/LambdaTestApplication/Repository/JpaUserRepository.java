package WhaTap.LambdaTestApplication.Repository;

import WhaTap.LambdaTestApplication.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

    private EntityManager em;

    @Autowired
    public JpaUserRepository(EntityManager em){
        this.em=em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String Email) {
        User user = em.find(User.class, Email);
        return Optional.ofNullable(user);
    }

    @Override
    public void updateCount(String Email, int Count) {
        User user = em.find(User.class, Email);
        user.setCount(Count+1);
        em.flush();
    }

}
