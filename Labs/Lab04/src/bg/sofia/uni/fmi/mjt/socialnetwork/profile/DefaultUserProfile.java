package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DefaultUserProfile  implements  UserProfile {

    private final String username;
    private final Set<Interest> interests = new HashSet<>();
    private final Set<UserProfile> friends = new HashSet<>();

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
    public Collection<Interest> getInterests() {
        return Collections.unmodifiableSet(interests);
    }

    @Override
    public Collection<UserProfile> getFriends() {
        return Collections.unmodifiableSet(friends);
    }

    @Override
    public boolean addFriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("Cannot add null as a friend");
        }
        if (this.equals(userProfile)) {
            throw new IllegalArgumentException("A user cannot add themselves as a friend");
        }

        if (!friends.add(userProfile)) {
            return false;
        }

        if (!userProfile.isFriend(this)) {
            if (!userProfile.addFriend(this)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean unfriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("Unfriending null user");
        }
        if (this.equals(userProfile)) {
            throw new IllegalArgumentException("A user cannot unfriend themselves");
        }

        if (!friends.remove(userProfile)) {
            return false;
        }

        if (userProfile.isFriend(this)) {
            if (!userProfile.unfriend(this)) { //if there is trouble with the unfriending method in the userProfile
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isFriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("Checking null user if is a friend.");
        }
        return friends.contains(userProfile);
    }
}
