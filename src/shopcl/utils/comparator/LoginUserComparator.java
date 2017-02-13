package shopcl.utils.comparator;

import java.util.Comparator;
import shopcl.model.User;

/**
 *
 * @author VakSF
 */
public class LoginUserComparator implements Comparator<User> {

    @Override
    public int compare(User user1, User user2) {
        
        if (user1.getUsername().equals(user2.getUsername())) {
            if (user1.getPassword().equals(user2.getPassword())) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

}
