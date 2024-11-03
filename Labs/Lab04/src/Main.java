import bg.sofia.uni.fmi.mjt.socialnetwork.SocialNetwork;
import bg.sofia.uni.fmi.mjt.socialnetwork.SocialNetworkImpl;
import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.ReactionType;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.DefaultUserProfile;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

public class Main {
    public static void main(String[] args) {
        SocialNetwork socialNetwork = new SocialNetworkImpl();

        try {
            // Създаваме потребители
            UserProfile user1 = new DefaultUserProfile("user1");
            UserProfile user2 = new DefaultUserProfile("user2");
            UserProfile user3 = new DefaultUserProfile("user3");
            UserProfile user4 = new DefaultUserProfile("user4");
            UserProfile user5 = new DefaultUserProfile("user5");

            // Регистрираме потребителите в социалната мрежа
            socialNetwork.registerUser(user1);
            socialNetwork.registerUser(user2);
            socialNetwork.registerUser(user3);
            socialNetwork.registerUser(user4);
            socialNetwork.registerUser(user5);

            // Добавяме приятели
            user1.addFriend(user2);
            user1.addFriend(user3);
            user2.addFriend(user4);
            user3.addFriend(user5);

            // Създаваме пост
            Post post1 = socialNetwork.post(user1, "Hello, this is my first post!");

            // Добавяне на различни реакции
            post1.addReaction(user2, ReactionType.LIKE);
            post1.addReaction(user3, ReactionType.LOVE);
            post1.addReaction(user4, ReactionType.ANGRY);
            post1.addReaction(user5, ReactionType.LAUGH);

            // Актуализиране на реакция
            post1.addReaction(user2, ReactionType.SAD); // Променяме реакцията на user2

            // Премахване на реакция
            post1.removeReaction(user4);

            boolean tr = user1.unfriend(user3);

            // Добавяме нови реакции след премахване
            post1.addReaction(user4, ReactionType.LOVE);
            post1.addReaction(user5, ReactionType.LIKE); // Актуализиране на реакцията на user5

            // Показваме броя на реакциите за всеки тип
            System.out.println("Reactions on the post:");
            for (ReactionType reactionType : post1.getAllReactions().keySet()) {
                System.out.println(reactionType + ": " + post1.getReactionCount(reactionType));
            }

            // Общо брой на всички реакции
            System.out.println("Total reactions count: " + post1.totalReactionsCount());

        } catch (UserRegistrationException e) {
            System.out.println("Error registering user: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
