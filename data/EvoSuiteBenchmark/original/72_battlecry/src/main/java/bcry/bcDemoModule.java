/* Battlecry V0.1
 * Copyright (C) 2003  Marek "Wansti" Moeckel
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package bcry;

import java.io.*;
import java.util.*;
import java.util.List.*;

/* BATTLECRY CUSTOM DEMO MODULE
 * A hard-coded version of a Battlecry module for use with BattlecryLive.
 * Created from a template by bcDemoMaker.
 */
class bcDemoModule {
   
   private Properties grammar;
   private Properties info;
   private List layout;
   private List wordLists;
   
   // CLASS CONTRUCTOR
   public bcDemoModule() {
      initDemoGrammar();
      initDemoInfo();
      initDemoLayout();
      initDemoWordLists();
   }
   
   public Properties getGrammar() {
      return grammar;
   }
   
   public Properties getInfo() {
      return info;
   }

   public List getLayout() {
      return layout;
   }
   
   public List getWordLists() {
      return wordLists;
   }

   private void initDemoGrammar() {
      grammar = new Properties();
      
      grammar.setProperty("CAN1","{'the gods made';noun;'and they saw that it was good'}");
      grammar.setProperty("STATUSREPORT","{verb_pp;direction;*'the';[OFTHE]}");
      grammar.setProperty("T5","{*'the';verb_pp;noun}");
      grammar.setProperty("WHEN","{'when'#'if';[WE]#[ACTIVITY];',';[WE]#[ACTIVITY]}");
      grammar.setProperty("SHORTCRY","{[CRY1]#[EXTVERB]}");
      grammar.setProperty("T4","{'the';noun;'to';verb}");
      grammar.setProperty("THE","{'the';[NOUN]}");
      grammar.setProperty("T3","{verb;'to';verb}");
      grammar.setProperty("T2","{verb;'for'#'to';*'the';noun}");
      grammar.setProperty("T1","{noun;'of';*'the';noun}");
      grammar.setProperty("TIMETO2","{('time'#verb);'to';verb;',';('time'#verb);'to';verb}");
      grammar.setProperty("PROPHECY","{*[SOMEONE];noun;*(verb_aux;'be');verb_pp;*(direction;[SOMEONE];noun)}");
      grammar.setProperty("TIMETO1","{*'it is';'time to';[EXTVERB]}");
      grammar.setProperty("ACTIVITY","{'the';[NOUN]#[OFTHE];[EXTVERB]}");
      grammar.setProperty("OFTHE","{[NOUN];'of';*'the';[NOUN]}");
      grammar.setProperty("OUR","{person_gen;[NOUN]}");
      grammar.setProperty("FOR","{'for the';[NOUN];*[OFTHE]}");
      grammar.setProperty("LET1","{person_obj;*[ALWAYS];[EXTVERB]}");
      grammar.setProperty("NOUN","{*adjective;noun}");
      grammar.setProperty("AFTERVERB","{[TO]#[FOR]}");
      grammar.setProperty("CRY2","{verb;'the';[NOUN]#[OFTHE]}");
      grammar.setProperty("CRY1","{verb;',';verb;',';verb}");
      grammar.setProperty("EXTVERB","{verb;*[AFTERVERB]}");
      grammar.setProperty("CANNED","{[CAN1]#[CAN2]#[CAN3]}");
      grammar.setProperty("DIRECTION","{direction;'the';noun;'of the';noun;'we';*verb_aux;verb}");
      grammar.setProperty("START","{[WE]#[BATTLECRY]#[TIMETO]#[WHEN]#[CANNED]#[ACTIVITY]#[ACTIVITY2]#[ACTIVITY3]#[LET]#[DIRECTION]#[COMMAND]#[PROPHECY]#[STATUSREPORT]#[AS]#[PROG1]#[PROG2]#[PROG3]#[PROG4]}");
      grammar.setProperty("WE2B","{*'the';[OFTHE]}");
      grammar.setProperty("WE2A","{'here'#'born';[TO]}");
      grammar.setProperty("TITLE","{[T1]#[T2]#[T3]#[T4]#[T5]#[NOUN]#[THE]}");
      grammar.setProperty("TO","{'to';verb;*[THE]}");
      grammar.setProperty("TIMETO","{[TIMETO1]#[TIMETO2]}");
      grammar.setProperty("COMMAND","{person;verb_aux;verb;direction;person_gen;noun}");
      grammar.setProperty("LET","{'let';[LET1]#[ACTIVITY];*'tonight'}");
      grammar.setProperty("BATTLECRY","{[CRY1]#[CRY2]#[EXTVERB]}");
      grammar.setProperty("WE3","{*'will';[EXTVERB]}");
      grammar.setProperty("WE","{person#[OUR];[WE1]#[WE2]#[WE3]}");
      grammar.setProperty("WE2","{[ARE];[WE2A]#[WE2B]}");
      grammar.setProperty("WE1","{'have come';[TO]}");
      grammar.setProperty("PROG4","{verb_prg;noun;'of';noun}");
      grammar.setProperty("SOMEONE","{person_gen#'the'}");
      grammar.setProperty("PROG3","{*('let';person_obj);verb_sense;'the';[NOUN];*verb_prg}");
      grammar.setProperty("AS","{'as';[SOMEONE];[NOUN];verb;[SOMEONE];noun}");
      grammar.setProperty("PROG2","{verb_prg;(direction;'the')#'the';noun}");
      grammar.setProperty("PROG1","{(adjective#verb_prg);noun;[ARE];verb_prg}");
      grammar.setProperty("ACTIVITY3","{'the';[NOUN]#[OFTHE];verb;adjective}");
      grammar.setProperty("ACTIVITY2","{verb;'the';[OFTHE]}");
      grammar.setProperty("CAN3","{'may';[THE];'be our';[NOUN]}");
      grammar.setProperty("ARE","{'are'#'will be'#'were'}");
      grammar.setProperty("CAN2","{noun;'is our religion'}");
      grammar.setProperty("ALWAYS","{'always'#'never'}");
   
   }
   
   private void initDemoInfo() {
   
      info = new Properties();
      
      info.setProperty("AUTHOR","Marek and Niels Moeckel");
      info.setProperty("OPTION_METRIC_TOLERANCE","1");
      info.setProperty("COMMENT","A Power Metal Module - the Battlecry default.");
      info.setProperty("VERSION","0.2cvs");
      info.setProperty("NAME","Battlecry");
      info.setProperty("OPTION_SYLLABLE_TOLERANCE","0");
   
   }
   
   private void initDemoLayout() {
      
      layout = new LinkedList();
      
      layout.add("verse:ababcdcd");
      layout.add("chorus:aabbcc");
      layout.add("verse:ababcdcd");
      layout.add("chorus:0");
      layout.add("bridge:2");
      layout.add("chorus:0");
      layout.add("lines:2");
      layout.add("CRY1");
   
   }
   
   private void initDemoWordLists() {
   
      wordLists = new LinkedList();
      bcWordList tempList;

      tempList = new bcWordList("adjective");
      tempList.addWord("morbid","M AO1 R B AH0 D");
      tempList.addWord("mercyful","M ER1 S IY0 F AH0 L");
      tempList.addWord("insane","IH2 N S EY1 N");
      tempList.addWord("stormy","S T AO1 R M IY0");
      tempList.addWord("silent","S AY1 L AH0 N T");
      tempList.addWord("good","G UH1 D");
      tempList.addWord("pure","P Y UH1 R");
      tempList.addWord("howling","HH AW1 L IH0 NG");
      tempList.addWord("true","T R UW1");
      tempList.addWord("brave","B R EY1 V");
      tempList.addWord("brutal","B R UW1 T AH0 L");
      tempList.addWord("hard","HH AA1 R D");
      tempList.addWord("victorious","V IH0 K T AO1 R IY0 AH0 S");
      tempList.addWord("loud","L AW1 D");
      tempList.addWord("glorious","G L AO1 R IY0 AH0 S");
      tempList.addWord("real","R IY1 L");
      tempList.addWord("holy","HH OW1 L IY0");
      tempList.addWord("far","F AA1 R");
      tempList.addWord("last","L AE1 S T");
      tempList.addWord("breathless","B R EH1 TH L AH0 S");
      tempList.addWord("heavenly","HH EH1 V AH0 N L IY0");
      tempList.addWord("crimson","K R IH1 M Z AH0 N");
      tempList.addWord("evil","IY1 V AH0 L");
      tempList.addWord("wild","W AY1 L D");
      tempList.addWord("blind","B L AY1 N D");
      tempList.addWord("metal","M EH1 T AH0 L");
      tempList.addWord("raging","R EY1 JH IH0 NG");
      tempList.addWord("powerful","P AW1 ER0 F AH0 L");
      tempList.addWord("cold","K OW1 L D");
      tempList.addWord("dark","D AA1 R K");
      tempList.addWord("fearful","F IH1 R F AH0 L");
      tempList.addWord("frightening","F R AY1 T AH0 N IH0 NG");
      tempList.addWord("royal","R OY1 AH0 L");
      tempList.addWord("endless","EH1 N D L AH0 S");
      tempList.addWord("unholy","AH0 N HH OW1 L IY0");
      tempList.addWord("near","N IH1 R");
      tempList.addWord("religious","R AH0 L IH1 JH AH0 S");
      tempList.addWord("mighty","M AY1 T IY0");
      tempList.addWord("grim","G R IH1 M");
      tempList.addWord("clear","K L IH1 R");
      tempList.addWord("aloud","AH0 L AW1 D");
      tempList.addWord("eternal","IH2 T ER1 N AH0 L");
      tempList.addWord("bloody","B L AH1 D IY0");
      tempList.addWord("high","HH AY1");
      tempList.addWord("old","OW1 L D");
      tempList.addWord("black","B L AE1 K");
      tempList.addWord("round","R AW1 N D");
      tempList.addWord("fast","F AE1 S T");
      tempList.addWord("united","Y UW0 N AY1 T AH0 D");
      tempList.addWord("immortal","IH2 M AO1 R T AH0 L");
      tempList.addWord("secret","S IY1 K R AH0 T");
      tempList.addWord("heavy","HH EH1 V IY0");
      tempList.addWord("magical","M AE1 JH IH0 K AH0 L");
      wordLists.add(tempList);

      tempList = new bcWordList("direction");
      tempList.addWord("into","IH2 N T UW1");
      tempList.addWord("out of","AW1 T AH1 V");
      tempList.addWord("on","AA1 N");
      tempList.addWord("over","OW1 V ER0");
      tempList.addWord("for","F AO1 R");
      tempList.addWord("by","B AY1");
      tempList.addWord("under","AH1 N D ER0");
      tempList.addWord("before","B AH0 F AO1 R");
      tempList.addWord("to","T UW1");
      tempList.addWord("from","F R AH1 M");
      tempList.addWord("with","W IH1 DH");
      tempList.addWord("of","AH1 V");
      wordLists.add(tempList);

      tempList = new bcWordList("noun");
      tempList.addWord("blood","B L AH1 D");
      tempList.addWord("souls","S OW1 L Z");
      tempList.addWord("challenge","CH AE1 L AH0 N JH");
      tempList.addWord("stone","S T OW1 N");
      tempList.addWord("evil","IY1 V AH0 L");
      tempList.addWord("wood","W UH1 D");
      tempList.addWord("call","K AO1 L");
      tempList.addWord("fate","F EY1 T");
      tempList.addWord("ring","R IH1 NG");
      tempList.addWord("road","R OW1 D");
      tempList.addWord("warcry","K R AY1");
      tempList.addWord("believers","B AH0 L IY1 V ER0 Z");
      tempList.addWord("magic","M AE1 JH IH0 K");
      tempList.addWord("hell","HH EH1 L");
      tempList.addWord("secret","S IY1 K R AH0 T");
      tempList.addWord("hammer","HH AE1 M ER0");
      tempList.addWord("sorcerers","S AO1 R S ER0 ER0 Z");
      tempList.addWord("pain","P EY1 N");
      tempList.addWord("wrath","R AE1 TH");
      tempList.addWord("side","S AY1 D");
      tempList.addWord("hall","HH AO1 L");
      tempList.addWord("light","L AY1 T");
      tempList.addWord("faith","F EY1 TH");
      tempList.addWord("ashes","AE1 SH AH0 Z");
      tempList.addWord("sharpness","SH AA1 R P N AH0 S");
      tempList.addWord("vengeance","V EH1 N JH AH0 N S");
      tempList.addWord("wing","W IH1 NG");
      tempList.addWord("destruction","D AH0 S T R AH1 K SH AH0 N");
      tempList.addWord("wind","W IH1 N D");
      tempList.addWord("horses","HH AO1 R S AH0 Z");
      tempList.addWord("demons","D IY1 M AH0 N Z");
      tempList.addWord("retribution","R EH2 T R AH0 B Y UW1 SH AH0 N");
      tempList.addWord("thing","TH IH1 NG");
      tempList.addWord("church","CH ER1 CH");
      tempList.addWord("fears","F IH1 R Z");
      tempList.addWord("lust","L AH1 S T");
      tempList.addWord("mountains","M AW1 N T AH0 N Z");
      tempList.addWord("agony","AE1 G AH0 N IY0");
      tempList.addWord("lie","L AY1");
      tempList.addWord("enemies","EH1 N AH0 M IY0 Z");
      tempList.addWord("beast","B IY1 S T");
      tempList.addWord("guardian","G AA1 R D IY0 AH0 N");
      tempList.addWord("war","W AO1 R");
      tempList.addWord("tears","T EH1 R Z");
      tempList.addWord("ice","AY1 S");
      tempList.addWord("muscles","M AH1 S AH0 L Z");
      tempList.addWord("masters","M AE1 S T ER0 Z");
      tempList.addWord("friend","F R EH1 N D");
      tempList.addWord("glory","G L AO1 R IY0");
      tempList.addWord("pride","P R AY1 D");
      tempList.addWord("will","W IH1 L");
      tempList.addWord("god","G AA1 D");
      tempList.addWord("creation","K R IY0 EY1 SH AH0 N");
      tempList.addWord("horde","HH AO1 R D");
      tempList.addWord("beer","B IH1 R");
      tempList.addWord("seas","S IY1 Z");
      tempList.addWord("crown","K R AW1 N");
      tempList.addWord("sword","S AO1 R D");
      tempList.addWord("brothers","B R AH1 DH ER0 Z");
      tempList.addWord("name","N EY1 M");
      tempList.addWord("hail","HH EY1 L");
      tempList.addWord("crowd","K R AW1 D");
      tempList.addWord("spell","S P EH1 L");
      tempList.addWord("lord","L AO1 R D");
      tempList.addWord("guitars","G AH0 T AA1 R Z");
      tempList.addWord("end","EH1 N D");
      tempList.addWord("gods","G AA1 D Z");
      tempList.addWord("power","P AW1 ER0");
      tempList.addWord("sanctuary","S AE1 NG K CH UW0 EH2 R IY0");
      tempList.addWord("prophet","P R AA1 F AH0 T");
      tempList.addWord("apocalypse","AH0 P AA1 K AH0 L IH2 P S");
      tempList.addWord("life","L AY1 F");
      tempList.addWord("chain","CH EY1 N");
      tempList.addWord("world","W ER1 L D");
      tempList.addWord("grave","G R EY1 V");
      tempList.addWord("corpses","K AO1 R P S AH0 Z");
      tempList.addWord("lies","L AY1 Z");
      tempList.addWord("sky","S K AY1");
      tempList.addWord("revolution","R EH2 V AH0 L UW1 SH AH0 N");
      tempList.addWord("dust","D AH1 S T");
      tempList.addWord("riders","R AY1 D ER0 Z");
      tempList.addWord("guide","G AY1 D");
      tempList.addWord("hate","HH EY1 T");
      tempList.addWord("losers","L UW1 Z ER0 Z");
      tempList.addWord("warriors","W AO1 R IY0 ER0 Z");
      tempList.addWord("rain","R EY1 N");
      tempList.addWord("fear","F IH1 R");
      tempList.addWord("chains","CH EY1 N Z");
      tempList.addWord("victory","V IH1 K T ER0 IY0");
      tempList.addWord("bloodlust","L AH1 S T");
      tempList.addWord("king","K IH1 NG");
      tempList.addWord("tear","T IH1 R");
      tempList.addWord("goals","G OW1 L Z");
      tempList.addWord("angel","EY1 N JH AH0 L");
      tempList.addWord("time","T AY1 M");
      tempList.addWord("throne","TH R OW1 N");
      tempList.addWord("anvil","AE1 N V AH0 L");
      tempList.addWord("fame","F EY1 M");
      tempList.addWord("thunder","TH AH1 N D ER0");
      tempList.addWord("death","D EH1 TH");
      tempList.addWord("salvation","S AE0 L V EY1 SH AH0 N");
      tempList.addWord("heart","HH AA1 R T");
      tempList.addWord("prison","P R IH1 Z AH0 N");
      tempList.addWord("story","S T AO1 R IY0");
      tempList.addWord("heavy metal","HH EH1 V IY0 M EH1 T AH0 L");
      tempList.addWord("reign","R EY1 N");
      tempList.addWord("beat","B IY1 T");
      tempList.addWord("triumph","T R AY1 AH0 M F");
      tempList.addWord("heed","HH IY1 D");
      tempList.addWord("unity","Y UW1 N AH0 T IY0");
      tempList.addWord("knife","N AY1 F");
      tempList.addWord("flashes","F L AE1 SH AH0 Z");
      tempList.addWord("sorrow","S AA1 R OW0");
      tempList.addWord("ground","G R AW1 N D");
      tempList.addWord("star","S T AA1 R");
      tempList.addWord("rage","R EY1 JH");
      tempList.addWord("justice","JH AH1 S T AH0 S");
      tempList.addWord("lightning","L AY1 T N IH0 NG");
      tempList.addWord("tale","T EY1 L");
      tempList.addWord("mercy","M ER1 S IY0");
      tempList.addWord("kingdom","K IH1 NG D AH0 M");
      tempList.addWord("century","S EH1 N CH ER0 IY0");
      tempList.addWord("bell","B EH1 L");
      tempList.addWord("stars","S T AA1 R Z");
      tempList.addWord("fantasy","F AE1 N T AH0 S IY0");
      tempList.addWord("wife","W AY1 F");
      tempList.addWord("defeat","D AH0 F IY1 T");
      tempList.addWord("curse","K ER1 S");
      tempList.addWord("diseases","D IH2 Z IY1 Z AH0 Z");
      tempList.addWord("fight","F AY1 T");
      tempList.addWord("devil","D EH1 V AH0 L");
      tempList.addWord("steel","S T IY1 L");
      tempList.addWord("earth","ER1 TH");
      tempList.addWord("sign","S AY1 N");
      tempList.addWord("army","AA1 R M IY0");
      tempList.addWord("religion","R AH0 L IH1 JH AH0 N");
      tempList.addWord("cries","K R AY1 Z");
      tempList.addWord("might","M AY1 T");
      tempList.addWord("scream","S K R IY1 M");
      tempList.addWord("lead","L EH1 D");
      tempList.addWord("wars","W AO1 R Z");
      tempList.addWord("decision","D AH0 S IH1 ZH AH0 N");
      tempList.addWord("ride","R AY1 D");
      tempList.addWord("sound","S AW1 N D");
      tempList.addWord("thor","TH AO1 R");
      tempList.addWord("leather","L EH1 DH ER0");
      tempList.addWord("weapon","W EH1 P AH0 N");
      tempList.addWord("prophecy","P R AA1 F AH0 S IY0");
      tempList.addWord("darkness","D AA1 R K N AH0 S");
      tempList.addWord("breath","B R EH1 TH");
      tempList.addWord("metal","M EH1 T AH0 L");
      tempList.addWord("sex","S EH1 K S");
      tempList.addWord("heat","HH IY1 T");
      tempList.addWord("battle","B AE1 T AH0 L");
      tempList.addWord("force","F AO1 R S");
      tempList.addWord("voices","V OY1 S AH0 Z");
      tempList.addWord("mind","M AY1 N D");
      wordLists.add(tempList);

      tempList = new bcWordList("person");
      tempList.addWord("we","W IY1");
      tempList.addWord("demons","D IY1 M AH0 N Z");
      tempList.addWord("satan","S EY1 T AH0 N");
      tempList.addWord("heroes","HH IH1 R OW0 Z");
      tempList.addWord("she","SH IY1");
      tempList.addWord("he","HH IY1");
      tempList.addWord("god","G AA1 D");
      tempList.addWord("you","Y UW1");
      tempList.addWord("angels","EY1 N JH AH0 L Z");
      tempList.addWord("I","AY1");
      tempList.addWord("everyone","EH1 V R IY0 W AH2 N");
      tempList.addWord("they","DH EY1");
      tempList.addWord("all of us","AO1 L AH1 V AH1 S");
      wordLists.add(tempList);

      tempList = new bcWordList("person_gen");
      tempList.addWord("her","HH ER0");
      tempList.addWord("our","AW1 ER0");
      tempList.addWord("your","Y AO1 R");
      tempList.addWord("my","M AY1");
      tempList.addWord("his","HH IH1 Z");
      tempList.addWord("their","DH EH1 R");
      wordLists.add(tempList);

      tempList = new bcWordList("person_obj");
      tempList.addWord("us","AH1 S");
      tempList.addWord("demons","D IY1 M AH0 N Z");
      tempList.addWord("him","HH IH1 M");
      tempList.addWord("satan","S EY1 T AH0 N");
      tempList.addWord("heroes","HH IH1 R OW0 Z");
      tempList.addWord("god","G AA1 D");
      tempList.addWord("them","DH EH1 M");
      tempList.addWord("her","HH ER0");
      tempList.addWord("me","M IY1");
      tempList.addWord("angels","EY1 N JH AH0 L Z");
      wordLists.add(tempList);

      tempList = new bcWordList("verb");
      tempList.addWord("gather","G AE1 DH ER0");
      tempList.addWord("call","K AO1 L");
      tempList.addWord("rise","R AY1 Z");
      tempList.addWord("cry","K R AY1");
      tempList.addWord("hail","HH EY1 L");
      tempList.addWord("sing","S IH1 NG");
      tempList.addWord("feel","F IY1 L");
      tempList.addWord("die","D AY1");
      tempList.addWord("regret","R IH0 G R EH1 T");
      tempList.addWord("fight","F AY1 T");
      tempList.addWord("suffer","S AH1 F ER0");
      tempList.addWord("fall","F AA1 L");
      tempList.addWord("defeat","D AH0 F IY1 T");
      tempList.addWord("sacrifice","S AE1 K R AH0 F AY2 S");
      tempList.addWord("begin","B IH0 G IH1 N");
      tempList.addWord("learn","L ER1 N");
      tempList.addWord("explode","IH0 K S P L OW1 D");
      tempList.addWord("burn","B ER1 N");
      tempList.addWord("fear","F IH1 R");
      tempList.addWord("end","EH1 N D");
      tempList.addWord("kill","K IH1 L");
      tempList.addWord("pound","P AW1 N D");
      tempList.addWord("light","L AY1 T");
      tempList.addWord("hit","HH IH1 T");
      tempList.addWord("break","B R EY1 K");
      tempList.addWord("rock","R AA1 K");
      tempList.addWord("split","S P L IH1 T");
      tempList.addWord("tell","T EH1 L");
      tempList.addWord("scream","S K R IY1 M");
      tempList.addWord("loose","L UW1 S");
      tempList.addWord("live","L AY1 V");
      tempList.addWord("beat","B IY1 T");
      tempList.addWord("promise","P R AA1 M AH0 S");
      tempList.addWord("slay","S L EY1");
      tempList.addWord("win","W IH1 N");
      tempList.addWord("crash","K R AE1 SH");
      tempList.addWord("pray","P R EY1");
      tempList.addWord("spend","S P EH1 N D");
      tempList.addWord("raise","R EY1 Z");
      tempList.addWord("forgive","F ER0 G IH1 V");
      tempList.addWord("return","R AH0 T ER1 N");
      tempList.addWord("battle","B AE1 T AH0 L");
      tempList.addWord("rule","R UW1 L");
      tempList.addWord("ride","R AY1 D");
      tempList.addWord("prepare","P R IY0 P EH1 R");
      tempList.addWord("forget","F ER0 G EH1 T");
      tempList.addWord("shout","SH AW1 T");
      tempList.addWord("unchain","AH0 N CH EY1 N");
      wordLists.add(tempList);

      tempList = new bcWordList("verb_prg");
      tempList.addWord("riding","R AY1 D IH0 NG");
      tempList.addWord("beginning","B IH0 G IH1 N IH0 NG");
      tempList.addWord("slaying","S L EY1 IH0 NG");
      tempList.addWord("suffering","S AH1 F ER0 IH0 NG");
      tempList.addWord("telling","T EH1 L IH0 NG");
      tempList.addWord("praying","P R EY1 IH0 NG");
      tempList.addWord("killing","K IH1 L IH0 NG");
      tempList.addWord("sacrificing","S AE1 K R AH0 F AY2 S IH0 NG");
      tempList.addWord("forgiving","F ER0 G IH1 V IH0 NG");
      tempList.addWord("crashing","K R AE1 SH IH0 NG");
      tempList.addWord("rising","R AY1 Z IH0 NG");
      tempList.addWord("dying","D AY1 IH0 NG");
      tempList.addWord("hitting","HH IH1 T IH0 NG");
      tempList.addWord("screaming","S K R IY1 M IH0 NG");
      tempList.addWord("spending","S P EH1 N D IH0 NG");
      tempList.addWord("pounding","P AW1 N D IH0 NG");
      tempList.addWord("rocking","R AA1 K IH0 NG");
      tempList.addWord("beating","B IY1 T IH0 NG");
      tempList.addWord("living","L IH1 V IH0 NG");
      tempList.addWord("breaking","B R EY1 K IH0 NG");
      tempList.addWord("winning","W IH1 N IH0 NG");
      tempList.addWord("fighting","F AY1 T IH0 NG");
      tempList.addWord("crying","K R AY1 IH0 NG");
      tempList.addWord("gathering","G AE1 DH ER0 IH0 NG");
      tempList.addWord("burning","B ER1 N IH0 NG");
      tempList.addWord("promising","P R AA1 M AH0 S IH0 NG");
      tempList.addWord("singing","S IH1 NG IH0 NG");
      tempList.addWord("forgetting","F ER0 G EH1 T IH0 NG");
      tempList.addWord("unchaining","AH0 N CH EY1 N IH0 NG");
      tempList.addWord("learning","L ER1 N IH0 NG");
      tempList.addWord("raising","R EY1 Z IH0 NG");
      tempList.addWord("hailing","HH EY1 L IH0 NG");
      tempList.addWord("preparing","P R IY0 P EH1 R IH0 NG");
      tempList.addWord("defeating","D AH0 F IY1 T IH0 NG");
      tempList.addWord("lighting","L AY1 T IH0 NG");
      tempList.addWord("regretting","R IH0 G R EH1 T IH0 NG");
      tempList.addWord("feeling","F IY1 L IH0 NG");
      tempList.addWord("ending","EH1 N D IH0 NG");
      tempList.addWord("calling","K AO1 L IH0 NG");
      tempList.addWord("falling","F AA1 L IH0 NG");
      tempList.addWord("returning","R AH0 T ER1 N IH0 NG");
      tempList.addWord("splitting","S P L IH1 T IH0 NG");
      tempList.addWord("exploding","IH0 K S P L OW1 D IH0 NG");
      tempList.addWord("shouting","SH AW1 T IH0 NG");
      wordLists.add(tempList);

      tempList = new bcWordList("verb_aux");
      tempList.addWord("will","W IH1 L");
      tempList.addWord("shall","SH AE1 L");
      tempList.addWord("may","M EY1");
      wordLists.add(tempList);

      tempList = new bcWordList("verb_sense");
      tempList.addWord("smell","S M EH1 L");
      tempList.addWord("hear","HH IH1 R");
      tempList.addWord("see","S IY1");
      tempList.addWord("taste","T EY1 S T");
      tempList.addWord("feel","F IY1 L");
      wordLists.add(tempList);

      tempList = new bcWordList("verb_pp");
      tempList.addWord("raised","R EY1 Z D");
      tempList.addWord("returned","R AH0 T ER1 N D");
      tempList.addWord("ripped","R IH1 P T");
      tempList.addWord("forged","F AO1 R JH D");
      tempList.addWord("prepared","P R IY0 P EH1 R D");
      tempList.addWord("sacrificed","S AE1 K R AH0 F AY2 S T");
      tempList.addWord("shouted","SH AW1 T AH0 D");
      tempList.addWord("unchained","AH0 N CH EY1 N D");
      tempList.addWord("beaten","B IY1 T AH0 N");
      tempList.addWord("fought","F AO1 T");
      tempList.addWord("slain","S L EY1 N");
      tempList.addWord("forgiven","F ER0 G IH1 V AH0 N");
      tempList.addWord("bound","B AW1 N D");
      tempList.addWord("forgotten","F ER0 G AA1 T AH0 N");
      tempList.addWord("suffered","S AH1 F ER0 D");
      tempList.addWord("promised","P R AA1 M AH0 S T");
      tempList.addWord("killed","K IH1 L D");
      tempList.addWord("ruled","R UW1 L D");
      tempList.addWord("begun","B IH0 G AH1 N");
      tempList.addWord("torn","T AO1 R N");
      tempList.addWord("exploded","IH0 K S P L OW1 D AH0 D");
      tempList.addWord("broken","B R OW1 K AH0 N");
      tempList.addWord("ridden","R IH1 D AH0 N");
      tempList.addWord("defeated","D AH0 F IY1 T AH0 D");
      tempList.addWord("hit","HH IH1 T");
      tempList.addWord("told","T OW1 L D");
      tempList.addWord("rotten","R AA1 T AH0 N");
      tempList.addWord("cried","K R AY1 D");
      tempList.addWord("feared","F IH1 R D");
      tempList.addWord("screamed","S K R IY1 M D");
      tempList.addWord("eaten","IY1 T AH0 N");
      tempList.addWord("battled","B AE1 T AH0 L D");
      tempList.addWord("sung","S AH1 NG");
      tempList.addWord("crashed","K R AE1 SH T");
      tempList.addWord("rocked","R AA1 K T");
      tempList.addWord("spent","S P EH1 N T");
      tempList.addWord("hailed","HH EY1 L D");
      tempList.addWord("lost","L AO1 S T");
      tempList.addWord("fallen","F AA1 L AH0 N");
      tempList.addWord("created","K R IY0 EY1 T AH0 D");
      tempList.addWord("won","W AH1 N");
      tempList.addWord("torn apart","T AO1 R N AH0 P AA1 R T");
      tempList.addWord("lived","L AY1 V D");
      tempList.addWord("died","D AY1 D");
      tempList.addWord("burnt","B ER1 N T");
      tempList.addWord("learned","L ER1 N D");
      tempList.addWord("ended","EH1 N D AH0 D");
      tempList.addWord("gathered","G AE1 DH ER0 D");
      wordLists.add(tempList);

      tempList = new bcWordList("BC_FROM_GRAMMAR");
      tempList.addWord("here","HH IH1 R");
      tempList.addWord(",","");
      tempList.addWord("it is","IH1 T IH1 Z");
      tempList.addWord("to","T UW1");
      tempList.addWord("never","N EH1 V ER0");
      tempList.addWord("the gods made","DH AH0 G AA1 D Z M EY1 D");
      tempList.addWord("be","B IY1");
      tempList.addWord("were","W ER0");
      tempList.addWord("will","W IH1 L");
      tempList.addWord("when","W EH1 N");
      tempList.addWord("will be","W IH1 L B IY1");
      tempList.addWord("are","AA1 R");
      tempList.addWord("time","T AY1 M");
      tempList.addWord("we","W IY1");
      tempList.addWord("the","DH AH0");
      tempList.addWord("born","B AO1 R N");
      tempList.addWord("time to","T AY1 M T UW1");
      tempList.addWord("have come","HH AE1 V K AH1 M");
      tempList.addWord("tonight","T AH0 N AY1 T");
      tempList.addWord("as","AE1 Z");
      tempList.addWord("for the","F AO1 R DH AH0");
      tempList.addWord("if","IH1 F");
      tempList.addWord("always","AO1 L W EY2 Z");
      tempList.addWord("of","AH1 V");
      tempList.addWord("let","L EH1 T");
      tempList.addWord("may","M EY1");
      tempList.addWord("of the","AH1 V DH AH0");
      tempList.addWord("is our religion","IH1 Z AW1 ER0 R AH0 L IH1 JH AH0 N");
      tempList.addWord("for","F AO1 R");
      tempList.addWord("be our","B IY1 AW1 ER0");
      tempList.addWord("and they saw that it was good","AE1 N D DH EY1 S AO1 DH AE1 T IH1 T W AA1 Z G UH1 D");
      wordLists.add(tempList);

      tempList = new bcWordList("BC_SPLIT_STRINGS");
      tempList.addWord("come","K AH1 M");
      tempList.addWord("saw","S AO1");
      tempList.addWord("to","T UW1");
      tempList.addWord("all","AO1 L");
      tempList.addWord("torn","T AO1 R N");
      tempList.addWord("have","HH AE1 V");
      tempList.addWord("metal","M EH1 T AH0 L");
      tempList.addWord("be","B IY1");
      tempList.addWord("will","W IH1 L");
      tempList.addWord("it","IH1 T");
      tempList.addWord("is","IH1 Z");
      tempList.addWord("religion","R AH0 L IH1 JH AH0 N");
      tempList.addWord("time","T AY1 M");
      tempList.addWord("gods","G AA1 D Z");
      tempList.addWord("that","DH AE1 T");
      tempList.addWord("us","AH1 S");
      tempList.addWord("good","G UH1 D");
      tempList.addWord("was","W AA1 Z");
      tempList.addWord("the","DH AH0");
      tempList.addWord("heavy","HH EH1 V IY0");
      tempList.addWord("apart","AH0 P AA1 R T");
      tempList.addWord("of","AH1 V");
      tempList.addWord("they","DH EY1");
      tempList.addWord("out","AW1 T");
      tempList.addWord("for","F AO1 R");
      tempList.addWord("made","M EY1 D");
      tempList.addWord("our","AW1 ER0");
      tempList.addWord("and","AE1 N D");
      tempList.addWord("","");
      wordLists.add(tempList);

   }
}
