package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.common.Deletable;
import Discarpet.script.values.common.DiscordValue;
import Discarpet.script.values.common.Renamable;
import carpet.script.value.BooleanValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.emoji.KnownCustomEmoji;

public class EmojiValue extends DiscordValue<Emoji> implements Deletable, Renamable {
    public EmojiValue(Emoji emoji) {
        super("emoji",emoji);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "mention_tag" -> StringValue.of(delegate.getMentionTag());
            case "unicode" -> ValueUtil.ofOptionalString(delegate.asUnicodeEmoji());
            case "is_animated" -> BooleanValue.of(delegate.isAnimated());
            case "is_unicode" -> BooleanValue.of(delegate.isUnicodeEmoji());
            case "is_custom" -> BooleanValue.of(delegate.isCustomEmoji());
            default -> Value.NULL;
        };
    }

    @Override
    public boolean delete() {
        return delegate instanceof KnownCustomEmoji knownCustomEmoji && ValueUtil.awaitFutureBoolean(knownCustomEmoji.delete(), "Failed to delete " + this.getTypeString());
    }

    @Override
    public boolean rename(String name) {
        return delegate instanceof KnownCustomEmoji knownCustomEmoji && ValueUtil.awaitFutureBoolean(knownCustomEmoji.updateName(name), "Could not rename emoji");
    }
}
