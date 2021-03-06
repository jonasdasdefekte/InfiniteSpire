package infinitespire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import infinitespire.InfiniteSpire;
import infinitespire.abstracts.Card;

public class OneForAll extends Card {
	
	public static final String ID = InfiniteSpire.createID("OneForAll");

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	public OneForAll() {
		super(ID, NAME, "img/infinitespire/cards/oneforall.png", COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
		this.misc = 2;
		this.baseMagicNumber = 2;
		this.magicNumber = 2;
		this.baseDamage = this.misc;
		this.exhaust = true;
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new OneForAll();
	}

	@Override
	public void upgrade() {
		if(!upgraded) {
			this.upgradeMagicNumber(1);
			this.upgradeName();
		}
	}

	@Override
    public void applyPowers() {
        this.baseDamage = this.misc;
        super.applyPowers();
        this.initializeDescription();
    }
	
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		
		AttackEffect effect = AttackEffect.BLUNT_LIGHT;
		
		if(this.damage >= 10 && this.damage < 20) {
			effect = AttackEffect.BLUNT_HEAVY;
		} else if(this.damage >= 20){
			effect = AttackEffect.SMASH;
		}
		
		AbstractDungeon.actionManager.addToBottom(new IncreaseMiscAction(this.uuid, this.misc, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(
				m,
				new DamageInfo(p, this.damage, DamageType.NORMAL), 
				effect));
	}
}
