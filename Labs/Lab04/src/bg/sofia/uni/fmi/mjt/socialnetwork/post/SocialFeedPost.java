package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class SocialFeedPost implements  Post {
    private final String uniqueId;
    private final UserProfile author;
    private final String content;
    private final LocalDateTime publishTime;
    private final Map<ReactionType, Set<UserProfile>> reactions = new HashMap<>();

    public SocialFeedPost(UserProfile author, String content) {
        uniqueId = generateUniqueId();
        this.author = author;
        this.content = content;
        publishTime = LocalDateTime.now();
        initializeMapWithReactions();
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    private void initializeMapWithReactions() {
        for (ReactionType type : ReactionType.values()) {
            reactions.put(type, new HashSet<>());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialFeedPost that = (SocialFeedPost) o;
        return Objects.equals(uniqueId, that.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uniqueId);
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public UserProfile getAuthor() {
        return author;
    }

    @Override
    public LocalDateTime getPublishedOn() {
        return publishTime;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public boolean addReaction(UserProfile userProfile, ReactionType reactionType) {
        if (userProfile == null || reactionType == null) {
            throw new IllegalArgumentException("User profile and reaction type cannot be null");
        }
        removeReaction(userProfile);
        return reactions.get(reactionType).add(userProfile);
    }

    @Override
    public boolean removeReaction(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile cannot be null");
        }

        boolean removed = false;
        for (Map.Entry<ReactionType, Set<UserProfile>> entry : reactions.entrySet()) {
            if (entry.getValue().remove(userProfile)) {
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public Map<ReactionType, Set<UserProfile>> getAllReactions() {
        return reactions;
    }

    @Override
    public int getReactionCount(ReactionType reactionType) {
        if (reactionType == null) {
            throw new IllegalArgumentException("Reaction type cannot be null");
        }
        return reactions.get(reactionType).size();
    }

    @Override
    public int totalReactionsCount() {
        int total = 0;
        for (Set<UserProfile> userProfiles : reactions.values()) {
            total += userProfiles.size();
        }
        return total;
    }
}
