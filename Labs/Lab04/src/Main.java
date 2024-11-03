import bg.sofia.uni.fmi.mjt.socialnetwork.SocialNetwork;
import bg.sofia.uni.fmi.mjt.socialnetwork.SocialNetworkImpl;
import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.ReactionType;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.DefaultUserProfile;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SocialNetwork socialNetwork = new SocialNetworkImpl();

        try {
            // Creating 15 users with different usernames and interests
            List<UserProfile> users = List.of(
                new DefaultUserProfile("Alice"),
                new DefaultUserProfile("Bob"),
                new DefaultUserProfile("Charlie"),
                new DefaultUserProfile("Diana"),
                new DefaultUserProfile("Eve"),
                new DefaultUserProfile("Frank"),
                new DefaultUserProfile("Grace"),
                new DefaultUserProfile("Hank"),
                new DefaultUserProfile("Ivy"),
                new DefaultUserProfile("Jack"),
                new DefaultUserProfile("Karen"),
                new DefaultUserProfile("Leo"),
                new DefaultUserProfile("Mona"),
                new DefaultUserProfile("Nate"),
                new DefaultUserProfile("Olivia")
            );

            // Assigning interests to each user
            Interest[][] interests = {
                {Interest.BOOKS, Interest.MUSIC},
                {Interest.SPORTS, Interest.MOVIES},
                {Interest.GAMES, Interest.FOOD},
                {Interest.TRAVEL, Interest.MUSIC},
                {Interest.BOOKS, Interest.SPORTS},
                {Interest.MOVIES, Interest.FOOD},
                {Interest.TRAVEL, Interest.GAMES},
                {Interest.MUSIC, Interest.BOOKS},
                {Interest.SPORTS, Interest.FOOD},
                {Interest.GAMES, Interest.MUSIC},
                {Interest.MOVIES, Interest.TRAVEL},
                {Interest.BOOKS, Interest.GAMES},
                {Interest.SPORTS, Interest.MUSIC},
                {Interest.FOOD, Interest.TRAVEL},
                {Interest.BOOKS, Interest.MOVIES}
            };

            // Registering users and assigning interests
            for (int i = 0; i < users.size(); i++) {
                socialNetwork.registerUser(users.get(i));
                for (Interest interest : interests[i]) {
                    users.get(i).addInterest(interest);
                }
            }

            // Establishing friendships to create a network
            users.get(0).addFriend(users.get(1));   // Alice and Bob
            users.get(0).addFriend(users.get(2));   // Alice and Charlie
            users.get(1).addFriend(users.get(3));   // Bob and Diana
            users.get(2).addFriend(users.get(3));   // Charlie and Diana
            users.get(2).addFriend(users.get(4));   // Charlie and Eve
            users.get(4).addFriend(users.get(5));   // Eve and Frank
            users.get(5).addFriend(users.get(6));   // Frank and Grace
            users.get(6).addFriend(users.get(7));   // Grace and Hank
            users.get(8).addFriend(users.get(9));   // Ivy and Jack
            users.get(9).addFriend(users.get(10));  // Jack and Karen
            users.get(10).addFriend(users.get(11)); // Karen and Leo
            users.get(11).addFriend(users.get(12)); // Leo and Mona
            users.get(12).addFriend(users.get(13)); // Mona and Nate
            users.get(13).addFriend(users.get(14)); // Nate and Olivia

            // Adding posts
            Post alicePost = socialNetwork.post(users.get(0), "Loving my new book on programming!");
            Post bobPost = socialNetwork.post(users.get(1), "Just watched an amazing movie!");
            Post charliePost = socialNetwork.post(users.get(2), "Excited about this new game!");
            Post dianaPost = socialNetwork.post(users.get(3), "Planning my next travel adventure.");

            // Adding reactions to posts
            alicePost.addReaction(users.get(2), ReactionType.LIKE);
            alicePost.addReaction(users.get(3), ReactionType.LOVE);
            bobPost.addReaction(users.get(0), ReactionType.LAUGH);
            charliePost.addReaction(users.get(4), ReactionType.ANGRY);
            dianaPost.addReaction(users.get(5), ReactionType.SAD);

            // Display all registered users
            System.out.println("All Registered Users:");
            for (UserProfile user : socialNetwork.getAllUsers()) {
                System.out.println(user.getUsername());
            }

            // Display all posts
            System.out.println("\nAll Posts:");
            for (Post p : socialNetwork.getPosts()) {
                System.out.println("Post by " + p.getAuthor().getUsername() + ": " + p.getContent());
            }

            // Get users who can view a specific post
            System.out.println("\nUsers who can view Alice's post:");
            for (UserProfile reachedUser : socialNetwork.getReachedUsers(alicePost)) {
                System.out.println(reachedUser.getUsername());
            }

            // Display sorted profiles by friend count
            System.out.println("\nProfiles Sorted by Friend Count:");
            for (UserProfile profile : socialNetwork.getAllProfilesSortedByFriendsCount()) {
                System.out.println(profile.getUsername() + " - Friends: " + profile.getFriends().size());
            }

        } catch (UserRegistrationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
