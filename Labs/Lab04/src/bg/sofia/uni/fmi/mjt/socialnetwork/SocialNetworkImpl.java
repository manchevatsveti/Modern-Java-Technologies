package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfileFriendsComparator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SocialNetworkImpl implements SocialNetwork {

    private final Set<UserProfile> users = new HashSet<>();
    private final Set<Post> posts = new HashSet<>();

    public SocialNetworkImpl() {

    }

    private boolean isRegistered(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile cannot be null");
        }
        return users.contains(userProfile);
    }

    @Override
    public void registerUser(UserProfile userProfile) throws UserRegistrationException {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile cannot be null");
        }
        if (isRegistered(userProfile)) {
            throw new UserRegistrationException("User is already registered");
        }
        users.add(userProfile);
    }

    @Override
    public Set<UserProfile> getAllUsers() {
        return Collections.unmodifiableSet(users);
    }

    @Override
    public Collection<Post> getPosts() {
        return Collections.unmodifiableSet(posts);
    }

    @Override
    public Post post(UserProfile userProfile, String content) throws UserRegistrationException {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile cannot be null");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        if (!isRegistered(userProfile)) {
            throw new UserRegistrationException("User is not registered");
        }

        Post post = new SocialFeedPost(userProfile, content);
        posts.add(post);
        return post;
    }

    @Override
    public Set<UserProfile> getReachedUsers(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }

        Set<UserProfile> reachedUsers = new HashSet<>();
        Set<UserProfile> visited = new HashSet<>();
        UserProfile author = post.getAuthor();

        dfs(author, reachedUsers, visited, author);

        return reachedUsers;
    }

    private void dfs(UserProfile user, Set<UserProfile> reachedUsers,
                     Set<UserProfile> visited, UserProfile initialUser) {
        if (user == null || initialUser == null) {
            throw new IllegalArgumentException("User profiles cannot be null");
        }

        visited.add(user);

        for (UserProfile friend : user.getFriends()) {
            if (!visited.contains(friend)) {
                if (!initialUser.getInterests().isEmpty() && !user.getInterests().isEmpty()) {
                    if (areThereCommonInterests(initialUser.getInterests(), friend.getInterests())) {
                        reachedUsers.add(friend);
                    }
                }
                dfs(friend, reachedUsers, visited, initialUser);
            }
        }
    }

    private boolean areThereCommonInterests(Collection<Interest> interests1, Collection<Interest> interests2) {
        if (interests1 == null || interests2 == null) {
            return false;
        }

        for (Interest interest : interests1) {
            if (interests2.contains(interest)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2)
        throws UserRegistrationException {
        if (userProfile1 == null || userProfile2 == null) {
            throw new IllegalArgumentException("User profiles cannot be null");
        }
        if (!isRegistered(userProfile1) || !isRegistered(userProfile2)) {
            throw new UserRegistrationException("One or both user profiles are not registered.");
        }
        Set<UserProfile> friendsOfUser1 = new HashSet<>(userProfile1.getFriends());
        Set<UserProfile> friendsOfUser2 = new HashSet<>(userProfile2.getFriends());

        friendsOfUser1.retainAll(friendsOfUser2);

        return friendsOfUser1;
    }

    @Override
    public SortedSet<UserProfile> getAllProfilesSortedByFriendsCount() {
        SortedSet<UserProfile> sortedProfiles = new TreeSet<>(new UserProfileFriendsComparator());

        sortedProfiles.addAll(users);
        return sortedProfiles;
    }
}
