package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DefaultUserProfile  implements  UserProfile {

    private String username;
    private Set<Interest> interests = new HashSet<>();
    private Set<UserProfile> friends = new HashSet<>();

    public DefaultUserProfile(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultUserProfile that = (DefaultUserProfile) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<Interest> getInterests() {
        return interests;
    }

    @Override
    public boolean addInterest(Interest interest) {
        if (interest == null) {
            throw new IllegalArgumentException("Adding null interest.");
        }
        return interests.add(interest);
    }

    @Override
    public boolean removeInterest(Interest interest) {
        if (interest == null) {
            throw new IllegalArgumentException("Removing null interest.");
        }
        return interests.remove(interest);
    }

    @Override
    public Collection<UserProfile> getFriends() {
        return friends;
    }

    @Override
    public boolean addFriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("Adding null friend.");
        }
        if (userProfile.equals(this)) {
            throw new IllegalArgumentException("Can't add oneself as a friend");
        }

        return (friends.add(userProfile) && userProfile.getFriends().add(this));
    }

    @Override
    public boolean unfriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("Unfriending null user");
        }
        return (friends.remove(userProfile) && userProfile.getFriends().remove(this));
    }

    @Override
    public boolean isFriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("Checking null user if is a friend.");
        }
        return friends.contains(userProfile);
    }
}
