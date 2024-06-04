package prismaticmod.patches;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class HandSelectAction extends AbstractGameAction {
    private boolean first;

    private final Predicate<AbstractCard> requirement;

    private final String text;

    private final AbstractPlayer p;

    private final ArrayList<AbstractCard> invalidCards;

    private final Consumer<ArrayList<AbstractCard>> process;

    private final Consumer<ArrayList<AbstractCard>> finale;

    private final boolean returnSelected;

    private final boolean anyAmount;

    private final boolean canPickZero;

    public HandSelectAction(int amount, Predicate<AbstractCard> requirement, Consumer<ArrayList<AbstractCard>> process, Consumer<ArrayList<AbstractCard>> finale, String selectionText, boolean returnSelected, boolean anyAmount, boolean canPickZero) {
        this.p = AbstractDungeon.player;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.amount = amount;
        this.first = true;
        this.requirement = requirement;
        this.process = process;
        this.finale = finale;
        this.text = selectionText;
        this.invalidCards = new ArrayList<>();
        this.returnSelected = returnSelected;
        this.anyAmount = anyAmount;
        this.canPickZero = canPickZero;
    }

    public HandSelectAction(int amount, Predicate<AbstractCard> requirement, Consumer<ArrayList<AbstractCard>> process, String selectionText) {
        this(amount, requirement, process, null, selectionText, true, false, false);
    }

    public void update() {
        if (this.first) {
            this.first = false;
            ArrayList<AbstractCard> validCards = new ArrayList<>();
            if (this.p.hand.isEmpty()) {
                if (this.finale != null)
                    this.finale.accept(new ArrayList<>());
                this.isDone = true;
                return;
            }
            for (AbstractCard c : this.p.hand.group) {
                if (this.requirement.test(c))
                    validCards.add(c);
            }
            if (validCards.isEmpty()) {
                if (this.finale != null)
                    this.finale.accept(new ArrayList<>());
                this.isDone = true;
                return;
            }
            if (validCards.size() <= this.amount && !this.anyAmount) {
                if (!this.returnSelected)
                    AbstractDungeon.player.hand.group.removeAll(validCards);
                this.process.accept(validCards);
                if (this.finale != null)
                    this.finale.accept(validCards);
                this.isDone = true;
                return;
            }
            this.invalidCards.addAll(this.p.hand.group);
            Objects.requireNonNull(validCards);
            this.invalidCards.removeIf(validCards::contains);
            this.p.hand.group.removeAll(this.invalidCards);
            if (this.p.hand.isEmpty()) {
                returnCards();
                if (!this.returnSelected)
                    AbstractDungeon.player.hand.group.removeAll(validCards);
                if (this.finale != null)
                    this.finale.accept(validCards);
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(this.text, this.amount, this.anyAmount, this.canPickZero);
            return;
        }
        ArrayList<AbstractCard> result = new ArrayList<>();
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            result.addAll(AbstractDungeon.handCardSelectScreen.selectedCards.group);
            this.process.accept(result);
            if (this.returnSelected)
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                    this.p.hand.addToTop(c);
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        if (this.finale != null)
            this.finale.accept(result);
        this.isDone = true;
    }

    private void returnCards() {
        for (AbstractCard c : this.invalidCards)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}
