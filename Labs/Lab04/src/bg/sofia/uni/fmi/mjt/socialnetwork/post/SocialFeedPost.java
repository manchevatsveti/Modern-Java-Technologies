package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.Collections;
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
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
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
        if (reactions.containsKey(reactionType) && reactions.get(reactionType).contains(userProfile)) {
            return false;
        }

        boolean isFirstReactionOfUser = !removeReaction(userProfile);

        Set<UserProfile> userProfiles = reactions.get(reactionType);
        if (userProfiles == null) {
            userProfiles = new HashSet<>();
            reactions.put(reactionType, userProfiles);
        }
        userProfiles.add(userProfile);

        return isFirstReactionOfUser;
    }

    @Override
    public boolean removeReaction(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile cannot be null");
        }

        boolean removed = false;
        Set<ReactionType> emptyReactionTypes = new HashSet<>();

        for (Map.Entry<ReactionType, Set<UserProfile>> entry : reactions.entrySet()) {
            Set<UserProfile> userProfiles = entry.getValue();
            if (userProfiles.remove(userProfile)) {
                removed = true;
                // Mark reaction type for removal if no more users react with it
                if (userProfiles.isEmpty()) {
                    emptyReactionTypes.add(entry.getKey());
                }
            }
        }

        // Remove all empty reaction types outside the loop to avoid concurrent modification
        for (ReactionType reactionType : emptyReactionTypes) {
            reactions.remove(reactionType);
        }

        return removed;
    }

    @Override
    public Map<ReactionType, Set<UserProfile>> getAllReactions() {
        Map<ReactionType, Set<UserProfile>> unmodifiableReactions = new HashMap<>();
        for (Map.Entry<ReactionType, Set<UserProfile>> entry : reactions.entrySet()) {
            unmodifiableReactions.put(entry.getKey(), Collections.unmodifiableSet(entry.getValue()));
        }
        return Collections.unmodifiableMap(unmodifiableReactions);
    }

    @Override
    public int getReactionCount(ReactionType reactionType) {
        if (reactionType == null) {
            throw new IllegalArgumentException("Reaction type cannot be null");
        }
        Set<UserProfile> users = reactions.get(reactionType);
        return users != null ? users.size() : 0;
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
