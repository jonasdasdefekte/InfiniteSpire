package infinitespire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import infinitespire.InfiniteSpire;
import infinitespire.abstracts.Quest;
import infinitespire.quests.RemoveCardQuest;

public class CardGroupPatch {
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.CardGroup", method="removeCard", paramtypes = {"com.megacrit.cardcrawl.cards.AbstractCard"})
	public static class PostRemoveCardHook {
		public static void Prefix(CardGroup __instance, AbstractCard card) {
			if(AbstractDungeon.player == null || __instance != AbstractDungeon.player.masterDeck) return;
			
			for(Quest q : InfiniteSpire.questLog) {
				if(q instanceof RemoveCardQuest) {
					q.incrementQuestSteps();
				}
			}
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.CardGroup", method = "getGroupWithoutBottledCards")
	public static class GetGroupWithoutBottledCards{
		@SpirePostfixPatch
		public static CardGroup removeBottledSoulCardFromGroup(CardGroup __result, CardGroup cards){
			__result.group.removeIf(AbstractCardPatch.Field.isBottledSoulCard::get);
			return __result;
		}
	}
}
