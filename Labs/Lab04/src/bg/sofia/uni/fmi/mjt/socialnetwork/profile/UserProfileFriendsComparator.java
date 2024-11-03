package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Comparator;

public class UserProfileFriendsComparator implements Comparator<UserProfile> {

    @Override
    public int compare(UserProfile user1, UserProfile user2) {
        if (user1.equals(user2)) {
            return 0;
        }

        return Integer.compare(user2.getFriends().size(), user1.getFriends().size());
    }
}
