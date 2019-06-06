package com.ociweb.jnb.jun2010.java.bowling;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.scenario.annotations.Alias;
import org.jbehave.scenario.annotations.Aliases;
import org.jbehave.scenario.annotations.BeforeScenario;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Named;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.steps.Steps;

import fj.data.Validation;


public class BowlingSteps extends Steps {
  private ArrayList<Integer> rolls;
  private Validation<ScoreBowlingGame.InvalidPinCountException, List<Integer>> scoreResult;

  @BeforeScenario
  public void resetRolls() {
    rolls = new ArrayList<Integer>();
  }

  @BeforeScenario
  public void resetScoreResult() {
      scoreResult = null;
  }
    
  @Given("a strike is bowled")
  public void aStrikeIsBowled() {
    rolls.add(10);
  }

  @Given("an $count count is bowled and spare is made")
  @Alias("a $count count is bowled and spare is made")
  public void countIsBowledAndSpare(Integer count) {
    rolls.add(count);
    rolls.add(10 - count);
  }

  @Given("an $count count is bowled and only $next is picked")
  @Aliases(values = {
   "an $count count is bowled and only $next are picked",
   "a $count count is bowled and only $next is picked",
   "a $count count is bowled and only $next are picked"
  })
  public void countIsBowledAndSomePicked(Integer count, Integer next) {
    rolls.add(count);
    rolls.add(next);
  }

  @Given("an $count count is bowled and none are picked")
  @Alias("a $count count is bowled and none are picked")
  public void countIsBowledAndNonePicked(Integer count) {
    countIsBowledAndSomePicked(count, 0);
  }

  @Given("an $count count is bowled")
  @Alias("a $count count is bowled")
  public void countIsBowled(Integer count) {
    rolls.add(count);
  }

  @When("scores are tallied")
  public void scoreAreTallied() {
    try {
        scoreResult = Validation.success(new ScoreBowlingGame().f(rolls));
    } catch (ScoreBowlingGame.InvalidPinCountException ex) {
        scoreResult = Validation.fail(ex);
    }
  }

  @Then("the score at frame $frame should be $score")
  @Alias("$score the score at frame $frame should be")
  public void scoreAtFrameShouldBe(@Named("frame") Integer frame, @Named("score") Integer score) {
      assertThat(scoreResult.isSuccess(), is(true));
      assertThat(scoreResult.success().get(frame - 1), is(score));
  }

  @Then("there should be no score at frame $frame")
  public void noScoreAtFrame(Integer frame) {
    assertThat(scoreResult.isSuccess() && scoreResult.success().size() < frame, is(true));
  }

  @Then("invalid pin count should be detected")
  public void  invalidPinCountShouldBeDetected() {
    assertThat(scoreResult.isFail(), is(true));
  }

  @Given("rolls look like $rolls")
  @Alias("rolls are <rolls>")
  public void rollsLookLike(@Named("rolls") String rollString) {
    rolls = new ArrayList<Integer>();
    char[] rollChars = rollString.toCharArray();
    Integer lastRoll = null;
    for (int i=0; i < rollChars.length; i++) {
        if (rollChars[i] == 'X') {
            lastRoll = 10;
        } else if (rollChars[i] >= '0' && rollChars[i] <= '9') {
            lastRoll = Integer.parseInt("" + rollChars[i]);
        } else if (rollChars[i] == '/') {
            lastRoll = 10 - lastRoll;
        } else if (rollChars[i] == '-') {
            lastRoll = 0;
        } else {
            lastRoll = null;
        }
        if (lastRoll != null) { rolls.add(lastRoll); };
    }
  }

  @Then("scores should be <scores>")
  public void scoreShouldBe(@Named("scores") String expectedScores) {
      assertThat(scoreResult.isSuccess(), is(true));
      String[] scoreArray = expectedScores.split(" +");
      assertThat(scoreResult.success().size(), is(scoreArray.length));
      for (int i=0; i < scoreArray.length; i++) {
          assertThat(scoreResult.success().get(i), is(new Integer(scoreArray[i])));
      }      
  }

  //private void  fail(String reason) { throw new AssertionError(reason); }
}
