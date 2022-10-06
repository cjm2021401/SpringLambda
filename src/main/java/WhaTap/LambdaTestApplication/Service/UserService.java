package WhaTap.LambdaTestApplication.Service;

import WhaTap.LambdaTestApplication.Domain.User;
import WhaTap.LambdaTestApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    /**
     * 정보등록
     */
    public void join(User user){
        check(user);
        userRepository.save(user);
    }

    /**
     * 데이터 확인
     */
    public void check(User user){
        userRepository.findByEmail(user.getEmail()).ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 이메일입니다");
        });
    }

    /**
     * 로그인
     */
    public boolean login(User user){
        Optional<User> optional = findOne(user.getEmail());
        if(optional.isPresent()){
            User u=optional.get();
            if(u.getCount()>3){
                return false;
            }

            return true;
        }
        else{
            join(user);
            return true;
        }
    }



    /**
     * 데이터 조회
     */
    public Optional<User> findOne(String Email) {
        return userRepository.findByEmail(Email);
    }

    /**
     * 카운트 증가
     */
    public void addCount(String Email){
        Optional<User> optional = findOne(Email);
        if(optional.isPresent()){
            User m=optional.get();
            userRepository.updateCount(m.getEmail(), m.getCount());
        }
    }
}
