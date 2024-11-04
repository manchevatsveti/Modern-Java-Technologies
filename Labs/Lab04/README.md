# Social Network 💬

This week, we’ll roll up our sleeves and create a simple social network to model users, friendship networks, posts, and reactions.

## Social Network

In the `bg.sofia.uni.fmi.mjt.socialnetwork` package, create a public class `SocialNetworkImpl`, which has a default public constructor and implements the `SocialNetwork` interface:

```java
package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;

public interface SocialNetwork {

    /**
     * Registers a user in the social network.
     *
     * @param userProfile the user profile to register
     * @throws IllegalArgumentException  if the user profile is null
     * @throws UserRegistrationException if the user profile is already registered
     */
    void registerUser(UserProfile userProfile) throws UserRegistrationException;

    /**
     * Returns all the registered users in the social network.
     *
     * @return unmodifiable set of all registered users (empty one if there are none).
     */
    Set<UserProfile> getAllUsers();

    /**
     * Posts a new post in the social network.
     *
     * @param userProfile the user profile that posts the content
     * @param content     the content of the post
     * @return the created post
     * @throws UserRegistrationException if the user profile is not registered
     * @throws IllegalArgumentException  if the user profile is null
     * @throws IllegalArgumentException  if the content is null or empty
     */
    Post post(UserProfile userProfile, String content) throws UserRegistrationException;

    /**
     * Returns all posts in the social network.
     *
     * @return unmodifiable collection of all posts (empty one if there are none).
     */
    Collection<Post> getPosts();

    /**
     * Returns a collection of unique user profiles that can see the specified post in their feed. A
     * user can view a post if both of the following conditions are met:
     * 1. The user has at least one common interest with the author of the post.
     * 2. The user has the author of the post in their network of friends.
     *
     * Two users are considered to be in the same network of friends if they are directly connected
     * or if there exists a chain of friends connecting them.
     *
     * @param post The post for which visibility is being determined
     * @return A set of user profiles that meet the visibility criteria (empty one if there are none).
     * @throws IllegalArgumentException if the post is null.
     */
    Set<UserProfile> getReachedUsers(Post post);

    /**
     * Returns a set of all mutual friends between the two users.
     *
     * @param userProfile1 the first user profile
     * @param userProfile2 the second user profile
     * @return a set of all mutual friends between the two users or an empty set if there are no
     * mutual friends
     * @throws UserRegistrationException if any of the user profiles is not registered
     * @throws IllegalArgumentException  if any of the user profiles is null
     */
    Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2)
        throws UserRegistrationException;

    /**
     * Returns a sorted set of all user profiles ordered by the number of friends they have in
     * descending order.
     *
     * @return a sorted set of all user profiles ordered by the number of friends they have in
     * descending order
     */
    SortedSet<UserProfile> getAllProfilesSortedByFriendsCount();

}
```

## User Profile

In the `bg.sofia.uni.fmi.mjt.socialnetwork.profile` package, create a public class `DefaultUserProfile` to model the users of the social network. It should have a constructor:

```java
public DefaultUserProfile(String username)
```

and should implement the `UserProfile` interface:

```java
package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;

public interface UserProfile {

    String getUsername();

    Collection<Interest> getInterests();

    boolean addInterest(Interest interest);

    boolean removeInterest(Interest interest);

    Collection<UserProfile> getFriends();

    boolean addFriend(UserProfile userProfile);

    boolean unfriend(UserProfile userProfile);

    boolean isFriend(UserProfile userProfile);
}
```

In our social network, friendships are a symmetric relationship.

### Enum: Interest

User interests are modeled by the following enum:

```java
package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

public enum Interest {
    SPORTS, BOOKS, TRAVEL, MUSIC, MOVIES, GAMES, FOOD
}
```

## Post

A post in the social network is modeled by the `SocialFeedPost` class in the `bg.sofia.uni.fmi.mjt.socialnetwork.post` package. The class should have a constructor:

```java
public SocialFeedPost(UserProfile author, String content)
```

and should implement the following interface:

```java
package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public interface Post {

    String getUniqueId();

    UserProfile getAuthor();

    LocalDateTime getPublishedOn();

    String getContent();

    boolean addReaction(UserProfile userProfile, ReactionType reactionType);

    boolean removeReaction(UserProfile userProfile);

    Map<ReactionType, Set<UserProfile>> getAllReactions();

    int getReactionCount(ReactionType reactionType);

    int totalReactionsCount();
}
```

### Enum: ReactionType

Reactions are modeled by the following enum:

```java
package bg.sofia.uni.fmi.mjt.socialnetwork.post;

public enum ReactionType {
    LIKE, LOVE, ANGRY, LAUGH, SAD
}
```

## Packages

```
src
└── bg.sofia.uni.fmi.mjt.socialnetwork
    ├── exception
    │      └── UserRegistrationException.java 
    ├── post
    │      ├── Post.java
    │      ├── ReactionType.java
    │      ├── SocialFeedPost.java
    │      └── (...)
    ├── profile
    │      ├── DefaultUserProfile.java
    │      ├── Interest.java
    │      ├── UserProfile.java
    │      └── (...)
    ├── SocialNetwork.java
    ├── SocialNetworkImpl.java
    └── (...)
```
