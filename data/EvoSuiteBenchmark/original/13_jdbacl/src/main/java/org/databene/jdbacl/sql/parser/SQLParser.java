// $ANTLR 3.3 Nov 30, 2010 12:45:30 /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g 2011-06-08 18:35:16

	package org.databene.jdbacl.sql.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

@SuppressWarnings("unused")
public class SQLParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INT", "NOT", "IDENTIFIER", "STRING", "QUOTED_NAME", "PRIMARY", "DEFAULT", "COMMENT", "CREATE_TABLE", "DROP_TABLE", "ALTER_TABLE", "TABLE_NAME", "COLUMN_NAME", "SIZE", "CREATE_SEQUENCE", "DROP_SEQUENCE", "NAME_LIST", "CREATE_INDEX", "INDEX_NAME", "TABLE_DETAILS", "COLUMN_SPEC", "ORA_CONFIGS", "COMMENT_TABLE", "COMMENT_COLUMN", "INVOCATION", "ARGUMENTS", "VALUE_LIST", "OR", "AND", "XOR", "EQ", "BANGEQ", "LTGT", "GT", "GE", "LT", "LE", "IS", "NULL", "IN", "BETWEEN", "PLUS", "SUB", "STAR", "SLASH", "PERCENT", "BARBAR", "ROWID", "NUMBER", "VARCHAR2", "NVARCHAR2", "BYTE", "CHAR", "DATE", "TIMESTAMP", "BEFORE", "WS", "';'", "'CREATE'", "'GLOBAL'", "'TEMPORARY'", "'TABLE'", "'('", "')'", "','", "'NCHAR2'", "'BLOB'", "'CLOB'", "'NCLOB'", "'CONSTRAINT'", "'KEY'", "'ORGANIZATION'", "'TABLESPACE'", "'PCTUSED'", "'PCTFREE'", "'INITRANS'", "'MAXTRANS'", "'COMPRESS'", "'ENABLE'", "'ROW'", "'MOVEMENT'", "'DISABLE'", "'ON'", "'COMMIT'", "'DELETE'", "'ROWS'", "'STORAGE'", "'INITIAL'", "'NEXT'", "'MINEXTENTS'", "'MAXEXTENTS'", "'PCTINCREASE'", "'BUFFER_POOL'", "'PARTITION'", "'BY'", "'RANGE'", "'HASH'", "'LIST'", "'SUBPARTITION'", "'PARTITIONS'", "'STORE'", "'VALUES'", "'LESS'", "'THAN'", "'SUBPARTITIONS'", "'LOGGING'", "'NOLOGGING'", "'NOCOMPRESS'", "'CACHE'", "'NOCACHE'", "'NOPARALLEL'", "'MONITORING'", "'RETENTION'", "'LOB'", "'AS'", "'CHUNK'", "'PCTVERSION'", "'INDEX'", "'ALTER'", "'DROP'", "'CASCADE'", "'ADD'", "'USING'", "'FOREIGN'", "'REFERENCES'", "'UNIQUE'", "'CHECK'", "'CONSTRAINTS'", "'INDEXTYPE'", "'SEQUENCE'", "'COMMENT'", "'COLUMN'", "'.'", "'TRIGGER'"
    };
    public static final int EOF=-1;
    public static final int T__61=61;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__73=73;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int T__84=84;
    public static final int T__85=85;
    public static final int T__86=86;
    public static final int T__87=87;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__90=90;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int T__99=99;
    public static final int T__100=100;
    public static final int T__101=101;
    public static final int T__102=102;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int T__110=110;
    public static final int T__111=111;
    public static final int T__112=112;
    public static final int T__113=113;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__118=118;
    public static final int T__119=119;
    public static final int T__120=120;
    public static final int T__121=121;
    public static final int T__122=122;
    public static final int T__123=123;
    public static final int T__124=124;
    public static final int T__125=125;
    public static final int T__126=126;
    public static final int T__127=127;
    public static final int T__128=128;
    public static final int T__129=129;
    public static final int T__130=130;
    public static final int T__131=131;
    public static final int T__132=132;
    public static final int T__133=133;
    public static final int T__134=134;
    public static final int T__135=135;
    public static final int T__136=136;
    public static final int T__137=137;
    public static final int INT=4;
    public static final int NOT=5;
    public static final int IDENTIFIER=6;
    public static final int STRING=7;
    public static final int QUOTED_NAME=8;
    public static final int PRIMARY=9;
    public static final int DEFAULT=10;
    public static final int COMMENT=11;
    public static final int CREATE_TABLE=12;
    public static final int DROP_TABLE=13;
    public static final int ALTER_TABLE=14;
    public static final int TABLE_NAME=15;
    public static final int COLUMN_NAME=16;
    public static final int SIZE=17;
    public static final int CREATE_SEQUENCE=18;
    public static final int DROP_SEQUENCE=19;
    public static final int NAME_LIST=20;
    public static final int CREATE_INDEX=21;
    public static final int INDEX_NAME=22;
    public static final int TABLE_DETAILS=23;
    public static final int COLUMN_SPEC=24;
    public static final int ORA_CONFIGS=25;
    public static final int COMMENT_TABLE=26;
    public static final int COMMENT_COLUMN=27;
    public static final int INVOCATION=28;
    public static final int ARGUMENTS=29;
    public static final int VALUE_LIST=30;
    public static final int OR=31;
    public static final int AND=32;
    public static final int XOR=33;
    public static final int EQ=34;
    public static final int BANGEQ=35;
    public static final int LTGT=36;
    public static final int GT=37;
    public static final int GE=38;
    public static final int LT=39;
    public static final int LE=40;
    public static final int IS=41;
    public static final int NULL=42;
    public static final int IN=43;
    public static final int BETWEEN=44;
    public static final int PLUS=45;
    public static final int SUB=46;
    public static final int STAR=47;
    public static final int SLASH=48;
    public static final int PERCENT=49;
    public static final int BARBAR=50;
    public static final int ROWID=51;
    public static final int NUMBER=52;
    public static final int VARCHAR2=53;
    public static final int NVARCHAR2=54;
    public static final int BYTE=55;
    public static final int CHAR=56;
    public static final int DATE=57;
    public static final int TIMESTAMP=58;
    public static final int BEFORE=59;
    public static final int WS=60;

    // delegates
    // delegators


        public SQLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SQLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return SQLParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g"; }


    	protected void mismatch(IntStream input, int ttype, BitSet follow)
    	  throws RecognitionException
    	{
    	  throw new MismatchedTokenException(ttype, input);
    	}
    	
    	public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow)
    	  throws RecognitionException
    	{
    	  throw e;
    	}


    public static class commands_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "commands"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:93:1: commands : ( command )* ;
    public final SQLParser.commands_return commands() throws RecognitionException {
        SQLParser.commands_return retval = new SQLParser.commands_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SQLParser.command_return command1 = null;



        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:93:9: ( ( command )* )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:93:11: ( command )*
            {
            root_0 = (Object)adaptor.nil();

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:93:11: ( command )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==62||(LA1_0>=122 && LA1_0<=123)||LA1_0==134) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:93:11: command
            	    {
            	    pushFollow(FOLLOW_command_in_commands58);
            	    command1=command();

            	    state._fsp--;

            	    adaptor.addChild(root_0, command1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "commands"

    public static class command_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "command"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:95:1: command : ( ( create_table ';' ) | ( comment ';' ) | ( drop_table ';' ) | ( alter_table ';' ) | ( create_index ';' ) | ( drop_index ';' ) | ( create_sequence ';' ) | ( drop_sequence ';' ) );
    public final SQLParser.command_return command() throws RecognitionException {
        SQLParser.command_return retval = new SQLParser.command_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal3=null;
        Token char_literal5=null;
        Token char_literal7=null;
        Token char_literal9=null;
        Token char_literal11=null;
        Token char_literal13=null;
        Token char_literal15=null;
        Token char_literal17=null;
        SQLParser.create_table_return create_table2 = null;

        SQLParser.comment_return comment4 = null;

        SQLParser.drop_table_return drop_table6 = null;

        SQLParser.alter_table_return alter_table8 = null;

        SQLParser.create_index_return create_index10 = null;

        SQLParser.drop_index_return drop_index12 = null;

        SQLParser.create_sequence_return create_sequence14 = null;

        SQLParser.drop_sequence_return drop_sequence16 = null;


        Object char_literal3_tree=null;
        Object char_literal5_tree=null;
        Object char_literal7_tree=null;
        Object char_literal9_tree=null;
        Object char_literal11_tree=null;
        Object char_literal13_tree=null;
        Object char_literal15_tree=null;
        Object char_literal17_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:95:9: ( ( create_table ';' ) | ( comment ';' ) | ( drop_table ';' ) | ( alter_table ';' ) | ( create_index ';' ) | ( drop_index ';' ) | ( create_sequence ';' ) | ( drop_sequence ';' ) )
            int alt2=8;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:95:11: ( create_table ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:95:11: ( create_table ';' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:95:12: create_table ';'
                    {
                    pushFollow(FOLLOW_create_table_in_command68);
                    create_table2=create_table();

                    state._fsp--;

                    adaptor.addChild(root_0, create_table2.getTree());
                    char_literal3=(Token)match(input,61,FOLLOW_61_in_command73); 

                    }


                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:96:4: ( comment ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:96:4: ( comment ';' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:96:5: comment ';'
                    {
                    pushFollow(FOLLOW_comment_in_command81);
                    comment4=comment();

                    state._fsp--;

                    adaptor.addChild(root_0, comment4.getTree());
                    char_literal5=(Token)match(input,61,FOLLOW_61_in_command91); 

                    }


                    }
                    break;
                case 3 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:97:4: ( drop_table ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:97:4: ( drop_table ';' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:97:5: drop_table ';'
                    {
                    pushFollow(FOLLOW_drop_table_in_command99);
                    drop_table6=drop_table();

                    state._fsp--;

                    adaptor.addChild(root_0, drop_table6.getTree());
                    char_literal7=(Token)match(input,61,FOLLOW_61_in_command106); 

                    }


                    }
                    break;
                case 4 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:98:4: ( alter_table ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:98:4: ( alter_table ';' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:98:5: alter_table ';'
                    {
                    pushFollow(FOLLOW_alter_table_in_command114);
                    alter_table8=alter_table();

                    state._fsp--;

                    adaptor.addChild(root_0, alter_table8.getTree());
                    char_literal9=(Token)match(input,61,FOLLOW_61_in_command120); 

                    }


                    }
                    break;
                case 5 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:99:4: ( create_index ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:99:4: ( create_index ';' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:99:5: create_index ';'
                    {
                    pushFollow(FOLLOW_create_index_in_command128);
                    create_index10=create_index();

                    state._fsp--;

                    adaptor.addChild(root_0, create_index10.getTree());
                    char_literal11=(Token)match(input,61,FOLLOW_61_in_command133); 

                    }


                    }
                    break;
                case 6 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:100:4: ( drop_index ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:100:4: ( drop_index ';' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:100:5: drop_index ';'
                    {
                    pushFollow(FOLLOW_drop_index_in_command141);
                    drop_index12=drop_index();

                    state._fsp--;

                    adaptor.addChild(root_0, drop_index12.getTree());
                    char_literal13=(Token)match(input,61,FOLLOW_61_in_command148); 

                    }


                    }
                    break;
                case 7 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:101:4: ( create_sequence ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:101:4: ( create_sequence ';' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:101:5: create_sequence ';'
                    {
                    pushFollow(FOLLOW_create_sequence_in_command156);
                    create_sequence14=create_sequence();

                    state._fsp--;

                    adaptor.addChild(root_0, create_sequence14.getTree());
                    char_literal15=(Token)match(input,61,FOLLOW_61_in_command158); 

                    }


                    }
                    break;
                case 8 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:102:4: ( drop_sequence ';' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:102:4: ( drop_sequence ';' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:102:5: drop_sequence ';'
                    {
                    pushFollow(FOLLOW_drop_sequence_in_command166);
                    drop_sequence16=drop_sequence();

                    state._fsp--;

                    adaptor.addChild(root_0, drop_sequence16.getTree());
                    char_literal17=(Token)match(input,61,FOLLOW_61_in_command170); 

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "command"

    public static class create_table_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "create_table"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:107:1: create_table : 'CREATE' ( 'GLOBAL' )? ( 'TEMPORARY' )? 'TABLE' table_name '(' table_details ')' ora_configs -> ^( CREATE_TABLE table_name table_details ora_configs ) ;
    public final SQLParser.create_table_return create_table() throws RecognitionException {
        SQLParser.create_table_return retval = new SQLParser.create_table_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal18=null;
        Token string_literal19=null;
        Token string_literal20=null;
        Token string_literal21=null;
        Token char_literal23=null;
        Token char_literal25=null;
        SQLParser.table_name_return table_name22 = null;

        SQLParser.table_details_return table_details24 = null;

        SQLParser.ora_configs_return ora_configs26 = null;


        Object string_literal18_tree=null;
        Object string_literal19_tree=null;
        Object string_literal20_tree=null;
        Object string_literal21_tree=null;
        Object char_literal23_tree=null;
        Object char_literal25_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
        RewriteRuleSubtreeStream stream_ora_configs=new RewriteRuleSubtreeStream(adaptor,"rule ora_configs");
        RewriteRuleSubtreeStream stream_table_name=new RewriteRuleSubtreeStream(adaptor,"rule table_name");
        RewriteRuleSubtreeStream stream_table_details=new RewriteRuleSubtreeStream(adaptor,"rule table_details");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:107:14: ( 'CREATE' ( 'GLOBAL' )? ( 'TEMPORARY' )? 'TABLE' table_name '(' table_details ')' ora_configs -> ^( CREATE_TABLE table_name table_details ora_configs ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:107:16: 'CREATE' ( 'GLOBAL' )? ( 'TEMPORARY' )? 'TABLE' table_name '(' table_details ')' ora_configs
            {
            string_literal18=(Token)match(input,62,FOLLOW_62_in_create_table184);  
            stream_62.add(string_literal18);

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:107:25: ( 'GLOBAL' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==63) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:107:25: 'GLOBAL'
                    {
                    string_literal19=(Token)match(input,63,FOLLOW_63_in_create_table186);  
                    stream_63.add(string_literal19);


                    }
                    break;

            }

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:107:35: ( 'TEMPORARY' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==64) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:107:35: 'TEMPORARY'
                    {
                    string_literal20=(Token)match(input,64,FOLLOW_64_in_create_table189);  
                    stream_64.add(string_literal20);


                    }
                    break;

            }

            string_literal21=(Token)match(input,65,FOLLOW_65_in_create_table192);  
            stream_65.add(string_literal21);

            pushFollow(FOLLOW_table_name_in_create_table194);
            table_name22=table_name();

            state._fsp--;

            stream_table_name.add(table_name22.getTree());
            char_literal23=(Token)match(input,66,FOLLOW_66_in_create_table196);  
            stream_66.add(char_literal23);

            pushFollow(FOLLOW_table_details_in_create_table198);
            table_details24=table_details();

            state._fsp--;

            stream_table_details.add(table_details24.getTree());
            char_literal25=(Token)match(input,67,FOLLOW_67_in_create_table200);  
            stream_67.add(char_literal25);

            pushFollow(FOLLOW_ora_configs_in_create_table202);
            ora_configs26=ora_configs();

            state._fsp--;

            stream_ora_configs.add(ora_configs26.getTree());


            // AST REWRITE
            // elements: table_details, ora_configs, table_name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 107:101: -> ^( CREATE_TABLE table_name table_details ora_configs )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:107:104: ^( CREATE_TABLE table_name table_details ora_configs )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CREATE_TABLE, "CREATE_TABLE"), root_1);

                adaptor.addChild(root_1, stream_table_name.nextTree());
                adaptor.addChild(root_1, stream_table_details.nextTree());
                adaptor.addChild(root_1, stream_ora_configs.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "create_table"

    public static class table_name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "table_name"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:108:1: table_name : qualified_name -> ^( TABLE_NAME qualified_name ) ;
    public final SQLParser.table_name_return table_name() throws RecognitionException {
        SQLParser.table_name_return retval = new SQLParser.table_name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SQLParser.qualified_name_return qualified_name27 = null;


        RewriteRuleSubtreeStream stream_qualified_name=new RewriteRuleSubtreeStream(adaptor,"rule qualified_name");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:108:12: ( qualified_name -> ^( TABLE_NAME qualified_name ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:108:14: qualified_name
            {
            pushFollow(FOLLOW_qualified_name_in_table_name221);
            qualified_name27=qualified_name();

            state._fsp--;

            stream_qualified_name.add(qualified_name27.getTree());


            // AST REWRITE
            // elements: qualified_name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 108:29: -> ^( TABLE_NAME qualified_name )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:108:32: ^( TABLE_NAME qualified_name )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TABLE_NAME, "TABLE_NAME"), root_1);

                adaptor.addChild(root_1, stream_qualified_name.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "table_name"

    public static class table_details_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "table_details"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:109:1: table_details : table_detail ( ',' table_detail )* -> ^( TABLE_DETAILS ( table_detail )* ) ;
    public final SQLParser.table_details_return table_details() throws RecognitionException {
        SQLParser.table_details_return retval = new SQLParser.table_details_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal29=null;
        SQLParser.table_detail_return table_detail28 = null;

        SQLParser.table_detail_return table_detail30 = null;


        Object char_literal29_tree=null;
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleSubtreeStream stream_table_detail=new RewriteRuleSubtreeStream(adaptor,"rule table_detail");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:109:15: ( table_detail ( ',' table_detail )* -> ^( TABLE_DETAILS ( table_detail )* ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:109:17: table_detail ( ',' table_detail )*
            {
            pushFollow(FOLLOW_table_detail_in_table_details236);
            table_detail28=table_detail();

            state._fsp--;

            stream_table_detail.add(table_detail28.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:109:30: ( ',' table_detail )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==68) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:109:31: ',' table_detail
            	    {
            	    char_literal29=(Token)match(input,68,FOLLOW_68_in_table_details239);  
            	    stream_68.add(char_literal29);

            	    pushFollow(FOLLOW_table_detail_in_table_details241);
            	    table_detail30=table_detail();

            	    state._fsp--;

            	    stream_table_detail.add(table_detail30.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);



            // AST REWRITE
            // elements: table_detail
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 109:50: -> ^( TABLE_DETAILS ( table_detail )* )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:109:53: ^( TABLE_DETAILS ( table_detail )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TABLE_DETAILS, "TABLE_DETAILS"), root_1);

                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:109:69: ( table_detail )*
                while ( stream_table_detail.hasNext() ) {
                    adaptor.addChild(root_1, stream_table_detail.nextTree());

                }
                stream_table_detail.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "table_details"

    public static class table_detail_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "table_detail"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:110:1: table_detail : ( column_spec | inline_constraint );
    public final SQLParser.table_detail_return table_detail() throws RecognitionException {
        SQLParser.table_detail_return retval = new SQLParser.table_detail_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SQLParser.column_spec_return column_spec31 = null;

        SQLParser.inline_constraint_return inline_constraint32 = null;



        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:110:14: ( column_spec | inline_constraint )
            int alt6=2;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
            case QUOTED_NAME:
            case DEFAULT:
            case COMMENT:
            case IS:
            case 62:
            case 65:
            case 74:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 86:
            case 90:
            case 98:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 121:
            case 122:
            case 123:
            case 129:
            case 133:
            case 135:
            case 137:
                {
                alt6=1;
                }
                break;
            case PRIMARY:
                {
                int LA6_2 = input.LA(2);

                if ( (LA6_2==74) ) {
                    alt6=2;
                }
                else if ( ((LA6_2>=ROWID && LA6_2<=NVARCHAR2)||(LA6_2>=CHAR && LA6_2<=TIMESTAMP)||(LA6_2>=69 && LA6_2<=72)) ) {
                    alt6=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 2, input);

                    throw nvae;
                }
                }
                break;
            case 73:
                {
                alt6=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:110:16: column_spec
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_column_spec_in_table_detail259);
                    column_spec31=column_spec();

                    state._fsp--;

                    adaptor.addChild(root_0, column_spec31.getTree());

                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:110:30: inline_constraint
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_inline_constraint_in_table_detail263);
                    inline_constraint32=inline_constraint();

                    state._fsp--;

                    adaptor.addChild(root_0, inline_constraint32.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "table_detail"

    public static class column_spec_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_spec"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:111:1: column_spec : name type ( size )? ( column_detail )* -> ^( COLUMN_SPEC name type ( size )? ( column_detail )* ) ;
    public final SQLParser.column_spec_return column_spec() throws RecognitionException {
        SQLParser.column_spec_return retval = new SQLParser.column_spec_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SQLParser.name_return name33 = null;

        SQLParser.type_return type34 = null;

        SQLParser.size_return size35 = null;

        SQLParser.column_detail_return column_detail36 = null;


        RewriteRuleSubtreeStream stream_column_detail=new RewriteRuleSubtreeStream(adaptor,"rule column_detail");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        RewriteRuleSubtreeStream stream_size=new RewriteRuleSubtreeStream(adaptor,"rule size");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:111:13: ( name type ( size )? ( column_detail )* -> ^( COLUMN_SPEC name type ( size )? ( column_detail )* ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:111:15: name type ( size )? ( column_detail )*
            {
            pushFollow(FOLLOW_name_in_column_spec270);
            name33=name();

            state._fsp--;

            stream_name.add(name33.getTree());
            pushFollow(FOLLOW_type_in_column_spec272);
            type34=type();

            state._fsp--;

            stream_type.add(type34.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:111:25: ( size )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==66) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:111:25: size
                    {
                    pushFollow(FOLLOW_size_in_column_spec274);
                    size35=size();

                    state._fsp--;

                    stream_size.add(size35.getTree());

                    }
                    break;

            }

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:111:31: ( column_detail )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==NOT||LA8_0==DEFAULT||LA8_0==NULL) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:111:31: column_detail
            	    {
            	    pushFollow(FOLLOW_column_detail_in_column_spec277);
            	    column_detail36=column_detail();

            	    state._fsp--;

            	    stream_column_detail.add(column_detail36.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);



            // AST REWRITE
            // elements: name, size, type, column_detail
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 111:46: -> ^( COLUMN_SPEC name type ( size )? ( column_detail )* )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:111:49: ^( COLUMN_SPEC name type ( size )? ( column_detail )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMN_SPEC, "COLUMN_SPEC"), root_1);

                adaptor.addChild(root_1, stream_name.nextTree());
                adaptor.addChild(root_1, stream_type.nextTree());
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:111:73: ( size )?
                if ( stream_size.hasNext() ) {
                    adaptor.addChild(root_1, stream_size.nextTree());

                }
                stream_size.reset();
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:111:79: ( column_detail )*
                while ( stream_column_detail.hasNext() ) {
                    adaptor.addChild(root_1, stream_column_detail.nextTree());

                }
                stream_column_detail.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_spec"

    public static class type_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "type"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:112:1: type : ( 'NUMBER' | 'VARCHAR2' | 'NVARCHAR2' | 'CHAR' | 'NCHAR2' | 'TIMESTAMP' | 'DATE' | 'BLOB' | 'CLOB' | 'NCLOB' | 'ROWID' );
    public final SQLParser.type_return type() throws RecognitionException {
        SQLParser.type_return retval = new SQLParser.type_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set37=null;

        Object set37_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:112:6: ( 'NUMBER' | 'VARCHAR2' | 'NVARCHAR2' | 'CHAR' | 'NCHAR2' | 'TIMESTAMP' | 'DATE' | 'BLOB' | 'CLOB' | 'NCLOB' | 'ROWID' )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:
            {
            root_0 = (Object)adaptor.nil();

            set37=(Token)input.LT(1);
            if ( (input.LA(1)>=ROWID && input.LA(1)<=NVARCHAR2)||(input.LA(1)>=CHAR && input.LA(1)<=TIMESTAMP)||(input.LA(1)>=69 && input.LA(1)<=72) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set37));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "type"

    public static class size_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "size"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:124:1: size : '(' INT ( size_uom )? ( fractionDigits )? ')' -> ^( SIZE INT ( size_uom )? ( fractionDigits )? ) ;
    public final SQLParser.size_return size() throws RecognitionException {
        SQLParser.size_return retval = new SQLParser.size_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal38=null;
        Token INT39=null;
        Token char_literal42=null;
        SQLParser.size_uom_return size_uom40 = null;

        SQLParser.fractionDigits_return fractionDigits41 = null;


        Object char_literal38_tree=null;
        Object INT39_tree=null;
        Object char_literal42_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleSubtreeStream stream_fractionDigits=new RewriteRuleSubtreeStream(adaptor,"rule fractionDigits");
        RewriteRuleSubtreeStream stream_size_uom=new RewriteRuleSubtreeStream(adaptor,"rule size_uom");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:124:7: ( '(' INT ( size_uom )? ( fractionDigits )? ')' -> ^( SIZE INT ( size_uom )? ( fractionDigits )? ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:124:9: '(' INT ( size_uom )? ( fractionDigits )? ')'
            {
            char_literal38=(Token)match(input,66,FOLLOW_66_in_size370);  
            stream_66.add(char_literal38);

            INT39=(Token)match(input,INT,FOLLOW_INT_in_size372);  
            stream_INT.add(INT39);

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:124:17: ( size_uom )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0>=BYTE && LA9_0<=CHAR)) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:124:17: size_uom
                    {
                    pushFollow(FOLLOW_size_uom_in_size374);
                    size_uom40=size_uom();

                    state._fsp--;

                    stream_size_uom.add(size_uom40.getTree());

                    }
                    break;

            }

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:124:27: ( fractionDigits )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==68) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:124:27: fractionDigits
                    {
                    pushFollow(FOLLOW_fractionDigits_in_size377);
                    fractionDigits41=fractionDigits();

                    state._fsp--;

                    stream_fractionDigits.add(fractionDigits41.getTree());

                    }
                    break;

            }

            char_literal42=(Token)match(input,67,FOLLOW_67_in_size380);  
            stream_67.add(char_literal42);



            // AST REWRITE
            // elements: fractionDigits, size_uom, INT
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 124:47: -> ^( SIZE INT ( size_uom )? ( fractionDigits )? )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:124:50: ^( SIZE INT ( size_uom )? ( fractionDigits )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SIZE, "SIZE"), root_1);

                adaptor.addChild(root_1, stream_INT.nextNode());
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:124:61: ( size_uom )?
                if ( stream_size_uom.hasNext() ) {
                    adaptor.addChild(root_1, stream_size_uom.nextTree());

                }
                stream_size_uom.reset();
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:124:71: ( fractionDigits )?
                if ( stream_fractionDigits.hasNext() ) {
                    adaptor.addChild(root_1, stream_fractionDigits.nextTree());

                }
                stream_fractionDigits.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "size"

    public static class fractionDigits_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fractionDigits"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:125:1: fractionDigits : ',' INT ;
    public final SQLParser.fractionDigits_return fractionDigits() throws RecognitionException {
        SQLParser.fractionDigits_return retval = new SQLParser.fractionDigits_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal43=null;
        Token INT44=null;

        Object char_literal43_tree=null;
        Object INT44_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:125:16: ( ',' INT )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:125:18: ',' INT
            {
            root_0 = (Object)adaptor.nil();

            char_literal43=(Token)match(input,68,FOLLOW_68_in_fractionDigits401); 
            INT44=(Token)match(input,INT,FOLLOW_INT_in_fractionDigits404); 
            INT44_tree = (Object)adaptor.create(INT44);
            adaptor.addChild(root_0, INT44_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "fractionDigits"

    public static class size_uom_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "size_uom"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:126:1: size_uom : ( 'BYTE' | 'CHAR' );
    public final SQLParser.size_uom_return size_uom() throws RecognitionException {
        SQLParser.size_uom_return retval = new SQLParser.size_uom_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set45=null;

        Object set45_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:126:10: ( 'BYTE' | 'CHAR' )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:
            {
            root_0 = (Object)adaptor.nil();

            set45=(Token)input.LT(1);
            if ( (input.LA(1)>=BYTE && input.LA(1)<=CHAR) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set45));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "size_uom"

    public static class column_detail_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_detail"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:127:1: column_detail : ( nullability | default_value );
    public final SQLParser.column_detail_return column_detail() throws RecognitionException {
        SQLParser.column_detail_return retval = new SQLParser.column_detail_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SQLParser.nullability_return nullability46 = null;

        SQLParser.default_value_return default_value47 = null;



        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:127:15: ( nullability | default_value )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==NOT||LA11_0==NULL) ) {
                alt11=1;
            }
            else if ( (LA11_0==DEFAULT) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:127:17: nullability
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_nullability_in_column_detail422);
                    nullability46=nullability();

                    state._fsp--;

                    adaptor.addChild(root_0, nullability46.getTree());

                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:127:31: default_value
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_default_value_in_column_detail426);
                    default_value47=default_value();

                    state._fsp--;

                    adaptor.addChild(root_0, default_value47.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_detail"

    public static class nullability_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "nullability"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:128:1: nullability : ( NOT )? 'NULL' ;
    public final SQLParser.nullability_return nullability() throws RecognitionException {
        SQLParser.nullability_return retval = new SQLParser.nullability_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NOT48=null;
        Token string_literal49=null;

        Object NOT48_tree=null;
        Object string_literal49_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:128:13: ( ( NOT )? 'NULL' )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:128:15: ( NOT )? 'NULL'
            {
            root_0 = (Object)adaptor.nil();

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:128:18: ( NOT )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==NOT) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:128:18: NOT
                    {
                    NOT48=(Token)match(input,NOT,FOLLOW_NOT_in_nullability433); 
                    NOT48_tree = (Object)adaptor.create(NOT48);
                    root_0 = (Object)adaptor.becomeRoot(NOT48_tree, root_0);


                    }
                    break;

            }

            string_literal49=(Token)match(input,NULL,FOLLOW_NULL_in_nullability437); 
            string_literal49_tree = (Object)adaptor.create(string_literal49);
            adaptor.addChild(root_0, string_literal49_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "nullability"

    public static class default_value_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "default_value"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:129:1: default_value : 'DEFAULT' value ;
    public final SQLParser.default_value_return default_value() throws RecognitionException {
        SQLParser.default_value_return retval = new SQLParser.default_value_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal50=null;
        SQLParser.value_return value51 = null;


        Object string_literal50_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:129:15: ( 'DEFAULT' value )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:129:17: 'DEFAULT' value
            {
            root_0 = (Object)adaptor.nil();

            string_literal50=(Token)match(input,DEFAULT,FOLLOW_DEFAULT_in_default_value444); 
            string_literal50_tree = (Object)adaptor.create(string_literal50);
            root_0 = (Object)adaptor.becomeRoot(string_literal50_tree, root_0);

            pushFollow(FOLLOW_value_in_default_value447);
            value51=value();

            state._fsp--;

            adaptor.addChild(root_0, value51.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "default_value"

    public static class inline_constraint_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "inline_constraint"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:131:1: inline_constraint : ( 'CONSTRAINT' name )? 'PRIMARY' 'KEY' '(' name_list ')' ;
    public final SQLParser.inline_constraint_return inline_constraint() throws RecognitionException {
        SQLParser.inline_constraint_return retval = new SQLParser.inline_constraint_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal52=null;
        Token string_literal54=null;
        Token string_literal55=null;
        Token char_literal56=null;
        Token char_literal58=null;
        SQLParser.name_return name53 = null;

        SQLParser.name_list_return name_list57 = null;


        Object string_literal52_tree=null;
        Object string_literal54_tree=null;
        Object string_literal55_tree=null;
        Object char_literal56_tree=null;
        Object char_literal58_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:131:18: ( ( 'CONSTRAINT' name )? 'PRIMARY' 'KEY' '(' name_list ')' )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:131:20: ( 'CONSTRAINT' name )? 'PRIMARY' 'KEY' '(' name_list ')'
            {
            root_0 = (Object)adaptor.nil();

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:131:20: ( 'CONSTRAINT' name )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==73) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:131:21: 'CONSTRAINT' name
                    {
                    string_literal52=(Token)match(input,73,FOLLOW_73_in_inline_constraint455); 
                    pushFollow(FOLLOW_name_in_inline_constraint458);
                    name53=name();

                    state._fsp--;

                    adaptor.addChild(root_0, name53.getTree());

                    }
                    break;

            }

            string_literal54=(Token)match(input,PRIMARY,FOLLOW_PRIMARY_in_inline_constraint462); 
            string_literal54_tree = (Object)adaptor.create(string_literal54);
            root_0 = (Object)adaptor.becomeRoot(string_literal54_tree, root_0);

            string_literal55=(Token)match(input,74,FOLLOW_74_in_inline_constraint465); 
            char_literal56=(Token)match(input,66,FOLLOW_66_in_inline_constraint468); 
            pushFollow(FOLLOW_name_list_in_inline_constraint471);
            name_list57=name_list();

            state._fsp--;

            adaptor.addChild(root_0, name_list57.getTree());
            char_literal58=(Token)match(input,67,FOLLOW_67_in_inline_constraint473); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "inline_constraint"

    public static class ora_configs_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ora_configs"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:133:1: ora_configs : ( ora_config )* -> ^( ORA_CONFIGS ( ora_config )* ) ;
    public final SQLParser.ora_configs_return ora_configs() throws RecognitionException {
        SQLParser.ora_configs_return retval = new SQLParser.ora_configs_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SQLParser.ora_config_return ora_config59 = null;


        RewriteRuleSubtreeStream stream_ora_config=new RewriteRuleSubtreeStream(adaptor,"rule ora_config");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:133:13: ( ( ora_config )* -> ^( ORA_CONFIGS ( ora_config )* ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:133:15: ( ora_config )*
            {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:133:15: ( ora_config )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>=75 && LA14_0<=82)||(LA14_0>=85 && LA14_0<=86)||LA14_0==90||LA14_0==97||(LA14_0>=109 && LA14_0<=117)) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:133:15: ora_config
            	    {
            	    pushFollow(FOLLOW_ora_config_in_ora_configs483);
            	    ora_config59=ora_config();

            	    state._fsp--;

            	    stream_ora_config.add(ora_config59.getTree());

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);



            // AST REWRITE
            // elements: ora_config
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 133:27: -> ^( ORA_CONFIGS ( ora_config )* )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:133:30: ^( ORA_CONFIGS ( ora_config )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ORA_CONFIGS, "ORA_CONFIGS"), root_1);

                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:133:44: ( ora_config )*
                while ( stream_ora_config.hasNext() ) {
                    adaptor.addChild(root_1, stream_ora_config.nextTree());

                }
                stream_ora_config.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "ora_configs"

    public static class ora_config_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ora_config"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:134:1: ora_config : ( ( 'ORGANIZATION' name ) | ( 'TABLESPACE' name ) | ( 'PCTUSED' INT ) | ( 'PCTFREE' INT ) | ( 'INITRANS' INT ) | ( 'MAXTRANS' INT ) | ( 'COMPRESS' INT ) | ora_storage_spec | ora_partition_spec | ora_flag | ora_lob | ( 'ENABLE' 'ROW' 'MOVEMENT' ) | ( 'DISABLE' 'ROW' 'MOVEMENT' ) | ( 'ON' 'COMMIT' 'DELETE' 'ROWS' ) );
    public final SQLParser.ora_config_return ora_config() throws RecognitionException {
        SQLParser.ora_config_return retval = new SQLParser.ora_config_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal60=null;
        Token string_literal62=null;
        Token string_literal64=null;
        Token INT65=null;
        Token string_literal66=null;
        Token INT67=null;
        Token string_literal68=null;
        Token INT69=null;
        Token string_literal70=null;
        Token INT71=null;
        Token string_literal72=null;
        Token INT73=null;
        Token string_literal78=null;
        Token string_literal79=null;
        Token string_literal80=null;
        Token string_literal81=null;
        Token string_literal82=null;
        Token string_literal83=null;
        Token string_literal84=null;
        Token string_literal85=null;
        Token string_literal86=null;
        Token string_literal87=null;
        SQLParser.name_return name61 = null;

        SQLParser.name_return name63 = null;

        SQLParser.ora_storage_spec_return ora_storage_spec74 = null;

        SQLParser.ora_partition_spec_return ora_partition_spec75 = null;

        SQLParser.ora_flag_return ora_flag76 = null;

        SQLParser.ora_lob_return ora_lob77 = null;


        Object string_literal60_tree=null;
        Object string_literal62_tree=null;
        Object string_literal64_tree=null;
        Object INT65_tree=null;
        Object string_literal66_tree=null;
        Object INT67_tree=null;
        Object string_literal68_tree=null;
        Object INT69_tree=null;
        Object string_literal70_tree=null;
        Object INT71_tree=null;
        Object string_literal72_tree=null;
        Object INT73_tree=null;
        Object string_literal78_tree=null;
        Object string_literal79_tree=null;
        Object string_literal80_tree=null;
        Object string_literal81_tree=null;
        Object string_literal82_tree=null;
        Object string_literal83_tree=null;
        Object string_literal84_tree=null;
        Object string_literal85_tree=null;
        Object string_literal86_tree=null;
        Object string_literal87_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:135:2: ( ( 'ORGANIZATION' name ) | ( 'TABLESPACE' name ) | ( 'PCTUSED' INT ) | ( 'PCTFREE' INT ) | ( 'INITRANS' INT ) | ( 'MAXTRANS' INT ) | ( 'COMPRESS' INT ) | ora_storage_spec | ora_partition_spec | ora_flag | ora_lob | ( 'ENABLE' 'ROW' 'MOVEMENT' ) | ( 'DISABLE' 'ROW' 'MOVEMENT' ) | ( 'ON' 'COMMIT' 'DELETE' 'ROWS' ) )
            int alt15=14;
            switch ( input.LA(1) ) {
            case 75:
                {
                alt15=1;
                }
                break;
            case 76:
                {
                alt15=2;
                }
                break;
            case 77:
                {
                alt15=3;
                }
                break;
            case 78:
                {
                alt15=4;
                }
                break;
            case 79:
                {
                alt15=5;
                }
                break;
            case 80:
                {
                alt15=6;
                }
                break;
            case 81:
                {
                alt15=7;
                }
                break;
            case 90:
                {
                alt15=8;
                }
                break;
            case 97:
                {
                alt15=9;
                }
                break;
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
                {
                alt15=10;
                }
                break;
            case 117:
                {
                alt15=11;
                }
                break;
            case 82:
                {
                alt15=12;
                }
                break;
            case 85:
                {
                alt15=13;
                }
                break;
            case 86:
                {
                alt15=14;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:135:4: ( 'ORGANIZATION' name )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:135:4: ( 'ORGANIZATION' name )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:135:5: 'ORGANIZATION' name
                    {
                    string_literal60=(Token)match(input,75,FOLLOW_75_in_ora_config502); 
                    string_literal60_tree = (Object)adaptor.create(string_literal60);
                    root_0 = (Object)adaptor.becomeRoot(string_literal60_tree, root_0);

                    pushFollow(FOLLOW_name_in_ora_config505);
                    name61=name();

                    state._fsp--;

                    adaptor.addChild(root_0, name61.getTree());

                    }


                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:136:4: ( 'TABLESPACE' name )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:136:4: ( 'TABLESPACE' name )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:136:5: 'TABLESPACE' name
                    {
                    string_literal62=(Token)match(input,76,FOLLOW_76_in_ora_config512); 
                    string_literal62_tree = (Object)adaptor.create(string_literal62);
                    root_0 = (Object)adaptor.becomeRoot(string_literal62_tree, root_0);

                    pushFollow(FOLLOW_name_in_ora_config515);
                    name63=name();

                    state._fsp--;

                    adaptor.addChild(root_0, name63.getTree());

                    }


                    }
                    break;
                case 3 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:137:4: ( 'PCTUSED' INT )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:137:4: ( 'PCTUSED' INT )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:137:5: 'PCTUSED' INT
                    {
                    string_literal64=(Token)match(input,77,FOLLOW_77_in_ora_config522); 
                    string_literal64_tree = (Object)adaptor.create(string_literal64);
                    root_0 = (Object)adaptor.becomeRoot(string_literal64_tree, root_0);

                    INT65=(Token)match(input,INT,FOLLOW_INT_in_ora_config525); 
                    INT65_tree = (Object)adaptor.create(INT65);
                    adaptor.addChild(root_0, INT65_tree);


                    }


                    }
                    break;
                case 4 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:138:4: ( 'PCTFREE' INT )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:138:4: ( 'PCTFREE' INT )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:138:5: 'PCTFREE' INT
                    {
                    string_literal66=(Token)match(input,78,FOLLOW_78_in_ora_config532); 
                    string_literal66_tree = (Object)adaptor.create(string_literal66);
                    root_0 = (Object)adaptor.becomeRoot(string_literal66_tree, root_0);

                    INT67=(Token)match(input,INT,FOLLOW_INT_in_ora_config535); 
                    INT67_tree = (Object)adaptor.create(INT67);
                    adaptor.addChild(root_0, INT67_tree);


                    }


                    }
                    break;
                case 5 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:139:4: ( 'INITRANS' INT )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:139:4: ( 'INITRANS' INT )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:139:5: 'INITRANS' INT
                    {
                    string_literal68=(Token)match(input,79,FOLLOW_79_in_ora_config542); 
                    string_literal68_tree = (Object)adaptor.create(string_literal68);
                    root_0 = (Object)adaptor.becomeRoot(string_literal68_tree, root_0);

                    INT69=(Token)match(input,INT,FOLLOW_INT_in_ora_config545); 
                    INT69_tree = (Object)adaptor.create(INT69);
                    adaptor.addChild(root_0, INT69_tree);


                    }


                    }
                    break;
                case 6 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:140:4: ( 'MAXTRANS' INT )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:140:4: ( 'MAXTRANS' INT )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:140:5: 'MAXTRANS' INT
                    {
                    string_literal70=(Token)match(input,80,FOLLOW_80_in_ora_config552); 
                    string_literal70_tree = (Object)adaptor.create(string_literal70);
                    root_0 = (Object)adaptor.becomeRoot(string_literal70_tree, root_0);

                    INT71=(Token)match(input,INT,FOLLOW_INT_in_ora_config555); 
                    INT71_tree = (Object)adaptor.create(INT71);
                    adaptor.addChild(root_0, INT71_tree);


                    }


                    }
                    break;
                case 7 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:141:4: ( 'COMPRESS' INT )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:141:4: ( 'COMPRESS' INT )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:141:5: 'COMPRESS' INT
                    {
                    string_literal72=(Token)match(input,81,FOLLOW_81_in_ora_config562); 
                    string_literal72_tree = (Object)adaptor.create(string_literal72);
                    root_0 = (Object)adaptor.becomeRoot(string_literal72_tree, root_0);

                    INT73=(Token)match(input,INT,FOLLOW_INT_in_ora_config565); 
                    INT73_tree = (Object)adaptor.create(INT73);
                    adaptor.addChild(root_0, INT73_tree);


                    }


                    }
                    break;
                case 8 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:142:4: ora_storage_spec
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ora_storage_spec_in_ora_config571);
                    ora_storage_spec74=ora_storage_spec();

                    state._fsp--;

                    adaptor.addChild(root_0, ora_storage_spec74.getTree());

                    }
                    break;
                case 9 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:143:4: ora_partition_spec
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ora_partition_spec_in_ora_config576);
                    ora_partition_spec75=ora_partition_spec();

                    state._fsp--;

                    adaptor.addChild(root_0, ora_partition_spec75.getTree());

                    }
                    break;
                case 10 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:144:4: ora_flag
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ora_flag_in_ora_config581);
                    ora_flag76=ora_flag();

                    state._fsp--;

                    adaptor.addChild(root_0, ora_flag76.getTree());

                    }
                    break;
                case 11 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:145:4: ora_lob
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ora_lob_in_ora_config586);
                    ora_lob77=ora_lob();

                    state._fsp--;

                    adaptor.addChild(root_0, ora_lob77.getTree());

                    }
                    break;
                case 12 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:146:4: ( 'ENABLE' 'ROW' 'MOVEMENT' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:146:4: ( 'ENABLE' 'ROW' 'MOVEMENT' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:146:5: 'ENABLE' 'ROW' 'MOVEMENT'
                    {
                    string_literal78=(Token)match(input,82,FOLLOW_82_in_ora_config592); 
                    string_literal78_tree = (Object)adaptor.create(string_literal78);
                    adaptor.addChild(root_0, string_literal78_tree);

                    string_literal79=(Token)match(input,83,FOLLOW_83_in_ora_config594); 
                    string_literal79_tree = (Object)adaptor.create(string_literal79);
                    root_0 = (Object)adaptor.becomeRoot(string_literal79_tree, root_0);

                    string_literal80=(Token)match(input,84,FOLLOW_84_in_ora_config597); 

                    }


                    }
                    break;
                case 13 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:147:4: ( 'DISABLE' 'ROW' 'MOVEMENT' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:147:4: ( 'DISABLE' 'ROW' 'MOVEMENT' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:147:5: 'DISABLE' 'ROW' 'MOVEMENT'
                    {
                    string_literal81=(Token)match(input,85,FOLLOW_85_in_ora_config605); 
                    string_literal81_tree = (Object)adaptor.create(string_literal81);
                    adaptor.addChild(root_0, string_literal81_tree);

                    string_literal82=(Token)match(input,83,FOLLOW_83_in_ora_config607); 
                    string_literal82_tree = (Object)adaptor.create(string_literal82);
                    root_0 = (Object)adaptor.becomeRoot(string_literal82_tree, root_0);

                    string_literal83=(Token)match(input,84,FOLLOW_84_in_ora_config610); 

                    }


                    }
                    break;
                case 14 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:148:4: ( 'ON' 'COMMIT' 'DELETE' 'ROWS' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:148:4: ( 'ON' 'COMMIT' 'DELETE' 'ROWS' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:148:5: 'ON' 'COMMIT' 'DELETE' 'ROWS'
                    {
                    string_literal84=(Token)match(input,86,FOLLOW_86_in_ora_config618); 
                    string_literal84_tree = (Object)adaptor.create(string_literal84);
                    adaptor.addChild(root_0, string_literal84_tree);

                    string_literal85=(Token)match(input,87,FOLLOW_87_in_ora_config620); 
                    string_literal85_tree = (Object)adaptor.create(string_literal85);
                    adaptor.addChild(root_0, string_literal85_tree);

                    string_literal86=(Token)match(input,88,FOLLOW_88_in_ora_config622); 
                    string_literal86_tree = (Object)adaptor.create(string_literal86);
                    adaptor.addChild(root_0, string_literal86_tree);

                    string_literal87=(Token)match(input,89,FOLLOW_89_in_ora_config624); 
                    string_literal87_tree = (Object)adaptor.create(string_literal87);
                    adaptor.addChild(root_0, string_literal87_tree);


                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "ora_config"

    public static class ora_key_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ora_key"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:151:1: ora_key : ( 'TABLESPACE' | 'PCTUSED' | 'PCTFREE' | 'INITRANS' | 'MAXTRANS' | 'STORAGE' | ora_flag );
    public final SQLParser.ora_key_return ora_key() throws RecognitionException {
        SQLParser.ora_key_return retval = new SQLParser.ora_key_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal88=null;
        Token string_literal89=null;
        Token string_literal90=null;
        Token string_literal91=null;
        Token string_literal92=null;
        Token string_literal93=null;
        SQLParser.ora_flag_return ora_flag94 = null;


        Object string_literal88_tree=null;
        Object string_literal89_tree=null;
        Object string_literal90_tree=null;
        Object string_literal91_tree=null;
        Object string_literal92_tree=null;
        Object string_literal93_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:151:10: ( 'TABLESPACE' | 'PCTUSED' | 'PCTFREE' | 'INITRANS' | 'MAXTRANS' | 'STORAGE' | ora_flag )
            int alt16=7;
            switch ( input.LA(1) ) {
            case 76:
                {
                alt16=1;
                }
                break;
            case 77:
                {
                alt16=2;
                }
                break;
            case 78:
                {
                alt16=3;
                }
                break;
            case 79:
                {
                alt16=4;
                }
                break;
            case 80:
                {
                alt16=5;
                }
                break;
            case 90:
                {
                alt16=6;
                }
                break;
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
                {
                alt16=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:151:12: 'TABLESPACE'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal88=(Token)match(input,76,FOLLOW_76_in_ora_key636); 
                    string_literal88_tree = (Object)adaptor.create(string_literal88);
                    adaptor.addChild(root_0, string_literal88_tree);


                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:151:27: 'PCTUSED'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal89=(Token)match(input,77,FOLLOW_77_in_ora_key640); 
                    string_literal89_tree = (Object)adaptor.create(string_literal89);
                    adaptor.addChild(root_0, string_literal89_tree);


                    }
                    break;
                case 3 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:151:39: 'PCTFREE'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal90=(Token)match(input,78,FOLLOW_78_in_ora_key644); 
                    string_literal90_tree = (Object)adaptor.create(string_literal90);
                    adaptor.addChild(root_0, string_literal90_tree);


                    }
                    break;
                case 4 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:151:51: 'INITRANS'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal91=(Token)match(input,79,FOLLOW_79_in_ora_key648); 
                    string_literal91_tree = (Object)adaptor.create(string_literal91);
                    adaptor.addChild(root_0, string_literal91_tree);


                    }
                    break;
                case 5 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:151:64: 'MAXTRANS'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal92=(Token)match(input,80,FOLLOW_80_in_ora_key652); 
                    string_literal92_tree = (Object)adaptor.create(string_literal92);
                    adaptor.addChild(root_0, string_literal92_tree);


                    }
                    break;
                case 6 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:151:77: 'STORAGE'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal93=(Token)match(input,90,FOLLOW_90_in_ora_key656); 
                    string_literal93_tree = (Object)adaptor.create(string_literal93);
                    adaptor.addChild(root_0, string_literal93_tree);


                    }
                    break;
                case 7 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:151:89: ora_flag
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ora_flag_in_ora_key660);
                    ora_flag94=ora_flag();

                    state._fsp--;

                    adaptor.addChild(root_0, ora_flag94.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "ora_key"

    public static class ora_storage_spec_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ora_storage_spec"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:152:1: ora_storage_spec : 'STORAGE' '(' ( ora_storage_opt )* ')' ;
    public final SQLParser.ora_storage_spec_return ora_storage_spec() throws RecognitionException {
        SQLParser.ora_storage_spec_return retval = new SQLParser.ora_storage_spec_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal95=null;
        Token char_literal96=null;
        Token char_literal98=null;
        SQLParser.ora_storage_opt_return ora_storage_opt97 = null;


        Object string_literal95_tree=null;
        Object char_literal96_tree=null;
        Object char_literal98_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:152:18: ( 'STORAGE' '(' ( ora_storage_opt )* ')' )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:152:20: 'STORAGE' '(' ( ora_storage_opt )* ')'
            {
            root_0 = (Object)adaptor.nil();

            string_literal95=(Token)match(input,90,FOLLOW_90_in_ora_storage_spec667); 
            string_literal95_tree = (Object)adaptor.create(string_literal95);
            root_0 = (Object)adaptor.becomeRoot(string_literal95_tree, root_0);

            char_literal96=(Token)match(input,66,FOLLOW_66_in_ora_storage_spec670); 
            char_literal96_tree = (Object)adaptor.create(char_literal96);
            adaptor.addChild(root_0, char_literal96_tree);

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:152:35: ( ora_storage_opt )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>=91 && LA17_0<=96)) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:152:35: ora_storage_opt
            	    {
            	    pushFollow(FOLLOW_ora_storage_opt_in_ora_storage_spec672);
            	    ora_storage_opt97=ora_storage_opt();

            	    state._fsp--;

            	    adaptor.addChild(root_0, ora_storage_opt97.getTree());

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            char_literal98=(Token)match(input,67,FOLLOW_67_in_ora_storage_spec675); 
            char_literal98_tree = (Object)adaptor.create(char_literal98);
            adaptor.addChild(root_0, char_literal98_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "ora_storage_spec"

    public static class ora_storage_opt_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ora_storage_opt"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:153:1: ora_storage_opt : ( ( 'INITIAL' INT IDENTIFIER ) | ( 'NEXT' INT ) | ( 'MINEXTENTS' INT ) | ( 'MAXEXTENTS' IDENTIFIER ) | ( 'PCTINCREASE' INT ) | ( 'BUFFER_POOL' name ) );
    public final SQLParser.ora_storage_opt_return ora_storage_opt() throws RecognitionException {
        SQLParser.ora_storage_opt_return retval = new SQLParser.ora_storage_opt_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal99=null;
        Token INT100=null;
        Token IDENTIFIER101=null;
        Token string_literal102=null;
        Token INT103=null;
        Token string_literal104=null;
        Token INT105=null;
        Token string_literal106=null;
        Token IDENTIFIER107=null;
        Token string_literal108=null;
        Token INT109=null;
        Token string_literal110=null;
        SQLParser.name_return name111 = null;


        Object string_literal99_tree=null;
        Object INT100_tree=null;
        Object IDENTIFIER101_tree=null;
        Object string_literal102_tree=null;
        Object INT103_tree=null;
        Object string_literal104_tree=null;
        Object INT105_tree=null;
        Object string_literal106_tree=null;
        Object IDENTIFIER107_tree=null;
        Object string_literal108_tree=null;
        Object INT109_tree=null;
        Object string_literal110_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:154:2: ( ( 'INITIAL' INT IDENTIFIER ) | ( 'NEXT' INT ) | ( 'MINEXTENTS' INT ) | ( 'MAXEXTENTS' IDENTIFIER ) | ( 'PCTINCREASE' INT ) | ( 'BUFFER_POOL' name ) )
            int alt18=6;
            switch ( input.LA(1) ) {
            case 91:
                {
                alt18=1;
                }
                break;
            case 92:
                {
                alt18=2;
                }
                break;
            case 93:
                {
                alt18=3;
                }
                break;
            case 94:
                {
                alt18=4;
                }
                break;
            case 95:
                {
                alt18=5;
                }
                break;
            case 96:
                {
                alt18=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:154:4: ( 'INITIAL' INT IDENTIFIER )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:154:4: ( 'INITIAL' INT IDENTIFIER )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:154:5: 'INITIAL' INT IDENTIFIER
                    {
                    string_literal99=(Token)match(input,91,FOLLOW_91_in_ora_storage_opt684); 
                    string_literal99_tree = (Object)adaptor.create(string_literal99);
                    root_0 = (Object)adaptor.becomeRoot(string_literal99_tree, root_0);

                    INT100=(Token)match(input,INT,FOLLOW_INT_in_ora_storage_opt691); 
                    INT100_tree = (Object)adaptor.create(INT100);
                    adaptor.addChild(root_0, INT100_tree);

                    IDENTIFIER101=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_ora_storage_opt693); 
                    IDENTIFIER101_tree = (Object)adaptor.create(IDENTIFIER101);
                    adaptor.addChild(root_0, IDENTIFIER101_tree);


                    }


                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:155:4: ( 'NEXT' INT )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:155:4: ( 'NEXT' INT )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:155:5: 'NEXT' INT
                    {
                    string_literal102=(Token)match(input,92,FOLLOW_92_in_ora_storage_opt700); 
                    string_literal102_tree = (Object)adaptor.create(string_literal102);
                    root_0 = (Object)adaptor.becomeRoot(string_literal102_tree, root_0);

                    INT103=(Token)match(input,INT,FOLLOW_INT_in_ora_storage_opt710); 
                    INT103_tree = (Object)adaptor.create(INT103);
                    adaptor.addChild(root_0, INT103_tree);


                    }


                    }
                    break;
                case 3 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:156:4: ( 'MINEXTENTS' INT )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:156:4: ( 'MINEXTENTS' INT )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:156:5: 'MINEXTENTS' INT
                    {
                    string_literal104=(Token)match(input,93,FOLLOW_93_in_ora_storage_opt717); 
                    string_literal104_tree = (Object)adaptor.create(string_literal104);
                    root_0 = (Object)adaptor.becomeRoot(string_literal104_tree, root_0);

                    INT105=(Token)match(input,INT,FOLLOW_INT_in_ora_storage_opt721); 
                    INT105_tree = (Object)adaptor.create(INT105);
                    adaptor.addChild(root_0, INT105_tree);


                    }


                    }
                    break;
                case 4 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:157:4: ( 'MAXEXTENTS' IDENTIFIER )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:157:4: ( 'MAXEXTENTS' IDENTIFIER )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:157:5: 'MAXEXTENTS' IDENTIFIER
                    {
                    string_literal106=(Token)match(input,94,FOLLOW_94_in_ora_storage_opt728); 
                    string_literal106_tree = (Object)adaptor.create(string_literal106);
                    root_0 = (Object)adaptor.becomeRoot(string_literal106_tree, root_0);

                    IDENTIFIER107=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_ora_storage_opt732); 
                    IDENTIFIER107_tree = (Object)adaptor.create(IDENTIFIER107);
                    adaptor.addChild(root_0, IDENTIFIER107_tree);


                    }


                    }
                    break;
                case 5 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:158:4: ( 'PCTINCREASE' INT )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:158:4: ( 'PCTINCREASE' INT )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:158:5: 'PCTINCREASE' INT
                    {
                    string_literal108=(Token)match(input,95,FOLLOW_95_in_ora_storage_opt739); 
                    string_literal108_tree = (Object)adaptor.create(string_literal108);
                    root_0 = (Object)adaptor.becomeRoot(string_literal108_tree, root_0);

                    INT109=(Token)match(input,INT,FOLLOW_INT_in_ora_storage_opt742); 
                    INT109_tree = (Object)adaptor.create(INT109);
                    adaptor.addChild(root_0, INT109_tree);


                    }


                    }
                    break;
                case 6 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:159:4: ( 'BUFFER_POOL' name )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:159:4: ( 'BUFFER_POOL' name )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:159:5: 'BUFFER_POOL' name
                    {
                    string_literal110=(Token)match(input,96,FOLLOW_96_in_ora_storage_opt749); 
                    string_literal110_tree = (Object)adaptor.create(string_literal110);
                    root_0 = (Object)adaptor.becomeRoot(string_literal110_tree, root_0);

                    pushFollow(FOLLOW_name_in_ora_storage_opt752);
                    name111=name();

                    state._fsp--;

                    adaptor.addChild(root_0, name111.getTree());

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "ora_storage_opt"

    public static class ora_partition_spec_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ora_partition_spec"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:162:1: ora_partition_spec : 'PARTITION' 'BY' ( 'RANGE' | 'HASH' | 'LIST' ) '(' name ')' ( 'SUBPARTITION' 'BY' ( 'RANGE' | 'HASH' ) '(' name ')' )? partition_part_config ;
    public final SQLParser.ora_partition_spec_return ora_partition_spec() throws RecognitionException {
        SQLParser.ora_partition_spec_return retval = new SQLParser.ora_partition_spec_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal112=null;
        Token string_literal113=null;
        Token set114=null;
        Token char_literal115=null;
        Token char_literal117=null;
        Token string_literal118=null;
        Token string_literal119=null;
        Token set120=null;
        Token char_literal121=null;
        Token char_literal123=null;
        SQLParser.name_return name116 = null;

        SQLParser.name_return name122 = null;

        SQLParser.partition_part_config_return partition_part_config124 = null;


        Object string_literal112_tree=null;
        Object string_literal113_tree=null;
        Object set114_tree=null;
        Object char_literal115_tree=null;
        Object char_literal117_tree=null;
        Object string_literal118_tree=null;
        Object string_literal119_tree=null;
        Object set120_tree=null;
        Object char_literal121_tree=null;
        Object char_literal123_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:162:20: ( 'PARTITION' 'BY' ( 'RANGE' | 'HASH' | 'LIST' ) '(' name ')' ( 'SUBPARTITION' 'BY' ( 'RANGE' | 'HASH' ) '(' name ')' )? partition_part_config )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:162:22: 'PARTITION' 'BY' ( 'RANGE' | 'HASH' | 'LIST' ) '(' name ')' ( 'SUBPARTITION' 'BY' ( 'RANGE' | 'HASH' ) '(' name ')' )? partition_part_config
            {
            root_0 = (Object)adaptor.nil();

            string_literal112=(Token)match(input,97,FOLLOW_97_in_ora_partition_spec764); 
            string_literal112_tree = (Object)adaptor.create(string_literal112);
            root_0 = (Object)adaptor.becomeRoot(string_literal112_tree, root_0);

            string_literal113=(Token)match(input,98,FOLLOW_98_in_ora_partition_spec767); 
            set114=(Token)input.LT(1);
            if ( (input.LA(1)>=99 && input.LA(1)<=101) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set114));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            char_literal115=(Token)match(input,66,FOLLOW_66_in_ora_partition_spec782); 
            pushFollow(FOLLOW_name_in_ora_partition_spec785);
            name116=name();

            state._fsp--;

            adaptor.addChild(root_0, name116.getTree());
            char_literal117=(Token)match(input,67,FOLLOW_67_in_ora_partition_spec787); 
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:162:84: ( 'SUBPARTITION' 'BY' ( 'RANGE' | 'HASH' ) '(' name ')' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==102) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:162:85: 'SUBPARTITION' 'BY' ( 'RANGE' | 'HASH' ) '(' name ')'
                    {
                    string_literal118=(Token)match(input,102,FOLLOW_102_in_ora_partition_spec791); 
                    string_literal118_tree = (Object)adaptor.create(string_literal118);
                    adaptor.addChild(root_0, string_literal118_tree);

                    string_literal119=(Token)match(input,98,FOLLOW_98_in_ora_partition_spec793); 
                    set120=(Token)input.LT(1);
                    if ( (input.LA(1)>=99 && input.LA(1)<=100) ) {
                        input.consume();
                        adaptor.addChild(root_0, (Object)adaptor.create(set120));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    char_literal121=(Token)match(input,66,FOLLOW_66_in_ora_partition_spec804); 
                    pushFollow(FOLLOW_name_in_ora_partition_spec807);
                    name122=name();

                    state._fsp--;

                    adaptor.addChild(root_0, name122.getTree());
                    char_literal123=(Token)match(input,67,FOLLOW_67_in_ora_partition_spec809); 

                    }
                    break;

            }

            pushFollow(FOLLOW_partition_part_config_in_ora_partition_spec814);
            partition_part_config124=partition_part_config();

            state._fsp--;

            adaptor.addChild(root_0, partition_part_config124.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "ora_partition_spec"

    public static class partition_part_config_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "partition_part_config"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:163:1: partition_part_config : ( '(' partition_parts ')' | 'PARTITIONS' INT 'STORE' 'IN' '(' name_list ')' );
    public final SQLParser.partition_part_config_return partition_part_config() throws RecognitionException {
        SQLParser.partition_part_config_return retval = new SQLParser.partition_part_config_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal125=null;
        Token char_literal127=null;
        Token string_literal128=null;
        Token INT129=null;
        Token string_literal130=null;
        Token string_literal131=null;
        Token char_literal132=null;
        Token char_literal134=null;
        SQLParser.partition_parts_return partition_parts126 = null;

        SQLParser.name_list_return name_list133 = null;


        Object char_literal125_tree=null;
        Object char_literal127_tree=null;
        Object string_literal128_tree=null;
        Object INT129_tree=null;
        Object string_literal130_tree=null;
        Object string_literal131_tree=null;
        Object char_literal132_tree=null;
        Object char_literal134_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:163:23: ( '(' partition_parts ')' | 'PARTITIONS' INT 'STORE' 'IN' '(' name_list ')' )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==66) ) {
                alt20=1;
            }
            else if ( (LA20_0==103) ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:163:25: '(' partition_parts ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal125=(Token)match(input,66,FOLLOW_66_in_partition_part_config821); 
                    pushFollow(FOLLOW_partition_parts_in_partition_part_config824);
                    partition_parts126=partition_parts();

                    state._fsp--;

                    adaptor.addChild(root_0, partition_parts126.getTree());
                    char_literal127=(Token)match(input,67,FOLLOW_67_in_partition_part_config826); 

                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:164:6: 'PARTITIONS' INT 'STORE' 'IN' '(' name_list ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal128=(Token)match(input,103,FOLLOW_103_in_partition_part_config834); 
                    string_literal128_tree = (Object)adaptor.create(string_literal128);
                    adaptor.addChild(root_0, string_literal128_tree);

                    INT129=(Token)match(input,INT,FOLLOW_INT_in_partition_part_config836); 
                    INT129_tree = (Object)adaptor.create(INT129);
                    adaptor.addChild(root_0, INT129_tree);

                    string_literal130=(Token)match(input,104,FOLLOW_104_in_partition_part_config838); 
                    string_literal130_tree = (Object)adaptor.create(string_literal130);
                    adaptor.addChild(root_0, string_literal130_tree);

                    string_literal131=(Token)match(input,IN,FOLLOW_IN_in_partition_part_config840); 
                    string_literal131_tree = (Object)adaptor.create(string_literal131);
                    adaptor.addChild(root_0, string_literal131_tree);

                    char_literal132=(Token)match(input,66,FOLLOW_66_in_partition_part_config842); 
                    char_literal132_tree = (Object)adaptor.create(char_literal132);
                    adaptor.addChild(root_0, char_literal132_tree);

                    pushFollow(FOLLOW_name_list_in_partition_part_config844);
                    name_list133=name_list();

                    state._fsp--;

                    adaptor.addChild(root_0, name_list133.getTree());
                    char_literal134=(Token)match(input,67,FOLLOW_67_in_partition_part_config846); 
                    char_literal134_tree = (Object)adaptor.create(char_literal134);
                    adaptor.addChild(root_0, char_literal134_tree);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "partition_part_config"

    public static class partition_parts_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "partition_parts"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:165:1: partition_parts : partition_part ( ',' partition_part )* ;
    public final SQLParser.partition_parts_return partition_parts() throws RecognitionException {
        SQLParser.partition_parts_return retval = new SQLParser.partition_parts_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal136=null;
        SQLParser.partition_part_return partition_part135 = null;

        SQLParser.partition_part_return partition_part137 = null;


        Object char_literal136_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:165:17: ( partition_part ( ',' partition_part )* )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:165:19: partition_part ( ',' partition_part )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_partition_part_in_partition_parts853);
            partition_part135=partition_part();

            state._fsp--;

            adaptor.addChild(root_0, partition_part135.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:165:34: ( ',' partition_part )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==68) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:165:35: ',' partition_part
            	    {
            	    char_literal136=(Token)match(input,68,FOLLOW_68_in_partition_parts856); 
            	    char_literal136_tree = (Object)adaptor.create(char_literal136);
            	    adaptor.addChild(root_0, char_literal136_tree);

            	    pushFollow(FOLLOW_partition_part_in_partition_parts858);
            	    partition_part137=partition_part();

            	    state._fsp--;

            	    adaptor.addChild(root_0, partition_part137.getTree());

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "partition_parts"

    public static class partition_part_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "partition_part"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:166:1: partition_part : 'PARTITION' name 'VALUES' partition_value_spec ora_configs ;
    public final SQLParser.partition_part_return partition_part() throws RecognitionException {
        SQLParser.partition_part_return retval = new SQLParser.partition_part_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal138=null;
        Token string_literal140=null;
        SQLParser.name_return name139 = null;

        SQLParser.partition_value_spec_return partition_value_spec141 = null;

        SQLParser.ora_configs_return ora_configs142 = null;


        Object string_literal138_tree=null;
        Object string_literal140_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:166:16: ( 'PARTITION' name 'VALUES' partition_value_spec ora_configs )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:166:18: 'PARTITION' name 'VALUES' partition_value_spec ora_configs
            {
            root_0 = (Object)adaptor.nil();

            string_literal138=(Token)match(input,97,FOLLOW_97_in_partition_part867); 
            string_literal138_tree = (Object)adaptor.create(string_literal138);
            root_0 = (Object)adaptor.becomeRoot(string_literal138_tree, root_0);

            pushFollow(FOLLOW_name_in_partition_part870);
            name139=name();

            state._fsp--;

            adaptor.addChild(root_0, name139.getTree());
            string_literal140=(Token)match(input,105,FOLLOW_105_in_partition_part872); 
            string_literal140_tree = (Object)adaptor.create(string_literal140);
            adaptor.addChild(root_0, string_literal140_tree);

            pushFollow(FOLLOW_partition_value_spec_in_partition_part874);
            partition_value_spec141=partition_value_spec();

            state._fsp--;

            adaptor.addChild(root_0, partition_value_spec141.getTree());
            pushFollow(FOLLOW_ora_configs_in_partition_part876);
            ora_configs142=ora_configs();

            state._fsp--;

            adaptor.addChild(root_0, ora_configs142.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "partition_part"

    public static class partition_value_spec_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "partition_value_spec"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:167:1: partition_value_spec : ( ( 'LESS' 'THAN' expression ora_configs sub_partition_config ) | ( '(' value_list ')' ) );
    public final SQLParser.partition_value_spec_return partition_value_spec() throws RecognitionException {
        SQLParser.partition_value_spec_return retval = new SQLParser.partition_value_spec_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal143=null;
        Token string_literal144=null;
        Token char_literal148=null;
        Token char_literal150=null;
        SQLParser.expression_return expression145 = null;

        SQLParser.ora_configs_return ora_configs146 = null;

        SQLParser.sub_partition_config_return sub_partition_config147 = null;

        SQLParser.value_list_return value_list149 = null;


        Object string_literal143_tree=null;
        Object string_literal144_tree=null;
        Object char_literal148_tree=null;
        Object char_literal150_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:167:22: ( ( 'LESS' 'THAN' expression ora_configs sub_partition_config ) | ( '(' value_list ')' ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==106) ) {
                alt22=1;
            }
            else if ( (LA22_0==66) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:167:24: ( 'LESS' 'THAN' expression ora_configs sub_partition_config )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:167:24: ( 'LESS' 'THAN' expression ora_configs sub_partition_config )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:167:25: 'LESS' 'THAN' expression ora_configs sub_partition_config
                    {
                    string_literal143=(Token)match(input,106,FOLLOW_106_in_partition_value_spec884); 
                    string_literal143_tree = (Object)adaptor.create(string_literal143);
                    adaptor.addChild(root_0, string_literal143_tree);

                    string_literal144=(Token)match(input,107,FOLLOW_107_in_partition_value_spec886); 
                    string_literal144_tree = (Object)adaptor.create(string_literal144);
                    adaptor.addChild(root_0, string_literal144_tree);

                    pushFollow(FOLLOW_expression_in_partition_value_spec888);
                    expression145=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression145.getTree());
                    pushFollow(FOLLOW_ora_configs_in_partition_value_spec890);
                    ora_configs146=ora_configs();

                    state._fsp--;

                    adaptor.addChild(root_0, ora_configs146.getTree());
                    pushFollow(FOLLOW_sub_partition_config_in_partition_value_spec892);
                    sub_partition_config147=sub_partition_config();

                    state._fsp--;

                    adaptor.addChild(root_0, sub_partition_config147.getTree());

                    }


                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:168:6: ( '(' value_list ')' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:168:6: ( '(' value_list ')' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:168:7: '(' value_list ')'
                    {
                    char_literal148=(Token)match(input,66,FOLLOW_66_in_partition_value_spec901); 
                    char_literal148_tree = (Object)adaptor.create(char_literal148);
                    adaptor.addChild(root_0, char_literal148_tree);

                    pushFollow(FOLLOW_value_list_in_partition_value_spec903);
                    value_list149=value_list();

                    state._fsp--;

                    adaptor.addChild(root_0, value_list149.getTree());
                    char_literal150=(Token)match(input,67,FOLLOW_67_in_partition_value_spec905); 
                    char_literal150_tree = (Object)adaptor.create(char_literal150);
                    adaptor.addChild(root_0, char_literal150_tree);


                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "partition_value_spec"

    public static class sub_partition_config_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sub_partition_config"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:169:1: sub_partition_config : 'SUBPARTITIONS' INT 'STORE' 'IN' '(' name_list ')' ;
    public final SQLParser.sub_partition_config_return sub_partition_config() throws RecognitionException {
        SQLParser.sub_partition_config_return retval = new SQLParser.sub_partition_config_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal151=null;
        Token INT152=null;
        Token string_literal153=null;
        Token string_literal154=null;
        Token char_literal155=null;
        Token char_literal157=null;
        SQLParser.name_list_return name_list156 = null;


        Object string_literal151_tree=null;
        Object INT152_tree=null;
        Object string_literal153_tree=null;
        Object string_literal154_tree=null;
        Object char_literal155_tree=null;
        Object char_literal157_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:169:22: ( 'SUBPARTITIONS' INT 'STORE' 'IN' '(' name_list ')' )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:169:24: 'SUBPARTITIONS' INT 'STORE' 'IN' '(' name_list ')'
            {
            root_0 = (Object)adaptor.nil();

            string_literal151=(Token)match(input,108,FOLLOW_108_in_sub_partition_config913); 
            string_literal151_tree = (Object)adaptor.create(string_literal151);
            root_0 = (Object)adaptor.becomeRoot(string_literal151_tree, root_0);

            INT152=(Token)match(input,INT,FOLLOW_INT_in_sub_partition_config916); 
            INT152_tree = (Object)adaptor.create(INT152);
            adaptor.addChild(root_0, INT152_tree);

            string_literal153=(Token)match(input,104,FOLLOW_104_in_sub_partition_config918); 
            string_literal153_tree = (Object)adaptor.create(string_literal153);
            adaptor.addChild(root_0, string_literal153_tree);

            string_literal154=(Token)match(input,IN,FOLLOW_IN_in_sub_partition_config920); 
            string_literal154_tree = (Object)adaptor.create(string_literal154);
            adaptor.addChild(root_0, string_literal154_tree);

            char_literal155=(Token)match(input,66,FOLLOW_66_in_sub_partition_config922); 
            char_literal155_tree = (Object)adaptor.create(char_literal155);
            adaptor.addChild(root_0, char_literal155_tree);

            pushFollow(FOLLOW_name_list_in_sub_partition_config924);
            name_list156=name_list();

            state._fsp--;

            adaptor.addChild(root_0, name_list156.getTree());
            char_literal157=(Token)match(input,67,FOLLOW_67_in_sub_partition_config926); 
            char_literal157_tree = (Object)adaptor.create(char_literal157);
            adaptor.addChild(root_0, char_literal157_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "sub_partition_config"

    public static class ora_flag_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ora_flag"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:170:1: ora_flag : ( 'LOGGING' | 'NOLOGGING' | 'NOCOMPRESS' | 'CACHE' | 'NOCACHE' | 'NOPARALLEL' | 'MONITORING' | 'RETENTION' );
    public final SQLParser.ora_flag_return ora_flag() throws RecognitionException {
        SQLParser.ora_flag_return retval = new SQLParser.ora_flag_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set158=null;

        Object set158_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:170:9: ( 'LOGGING' | 'NOLOGGING' | 'NOCOMPRESS' | 'CACHE' | 'NOCACHE' | 'NOPARALLEL' | 'MONITORING' | 'RETENTION' )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:
            {
            root_0 = (Object)adaptor.nil();

            set158=(Token)input.LT(1);
            if ( (input.LA(1)>=109 && input.LA(1)<=116) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set158));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "ora_flag"

    public static class ora_lob_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ora_lob"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:179:1: ora_lob : 'LOB' '(' column_name ')' 'STORE' 'AS' '(' ( ora_lob_config )* ')' ;
    public final SQLParser.ora_lob_return ora_lob() throws RecognitionException {
        SQLParser.ora_lob_return retval = new SQLParser.ora_lob_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal159=null;
        Token char_literal160=null;
        Token char_literal162=null;
        Token string_literal163=null;
        Token string_literal164=null;
        Token char_literal165=null;
        Token char_literal167=null;
        SQLParser.column_name_return column_name161 = null;

        SQLParser.ora_lob_config_return ora_lob_config166 = null;


        Object string_literal159_tree=null;
        Object char_literal160_tree=null;
        Object char_literal162_tree=null;
        Object string_literal163_tree=null;
        Object string_literal164_tree=null;
        Object char_literal165_tree=null;
        Object char_literal167_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:179:9: ( 'LOB' '(' column_name ')' 'STORE' 'AS' '(' ( ora_lob_config )* ')' )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:179:11: 'LOB' '(' column_name ')' 'STORE' 'AS' '(' ( ora_lob_config )* ')'
            {
            root_0 = (Object)adaptor.nil();

            string_literal159=(Token)match(input,117,FOLLOW_117_in_ora_lob981); 
            string_literal159_tree = (Object)adaptor.create(string_literal159);
            adaptor.addChild(root_0, string_literal159_tree);

            char_literal160=(Token)match(input,66,FOLLOW_66_in_ora_lob983); 
            char_literal160_tree = (Object)adaptor.create(char_literal160);
            adaptor.addChild(root_0, char_literal160_tree);

            pushFollow(FOLLOW_column_name_in_ora_lob985);
            column_name161=column_name();

            state._fsp--;

            adaptor.addChild(root_0, column_name161.getTree());
            char_literal162=(Token)match(input,67,FOLLOW_67_in_ora_lob987); 
            char_literal162_tree = (Object)adaptor.create(char_literal162);
            adaptor.addChild(root_0, char_literal162_tree);

            string_literal163=(Token)match(input,104,FOLLOW_104_in_ora_lob989); 
            string_literal163_tree = (Object)adaptor.create(string_literal163);
            adaptor.addChild(root_0, string_literal163_tree);

            string_literal164=(Token)match(input,118,FOLLOW_118_in_ora_lob991); 
            string_literal164_tree = (Object)adaptor.create(string_literal164);
            adaptor.addChild(root_0, string_literal164_tree);

            char_literal165=(Token)match(input,66,FOLLOW_66_in_ora_lob993); 
            char_literal165_tree = (Object)adaptor.create(char_literal165);
            adaptor.addChild(root_0, char_literal165_tree);

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:179:54: ( ora_lob_config )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==76||LA23_0==82||LA23_0==90||(LA23_0>=109 && LA23_0<=116)||(LA23_0>=119 && LA23_0<=121)) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:179:54: ora_lob_config
            	    {
            	    pushFollow(FOLLOW_ora_lob_config_in_ora_lob995);
            	    ora_lob_config166=ora_lob_config();

            	    state._fsp--;

            	    adaptor.addChild(root_0, ora_lob_config166.getTree());

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);

            char_literal167=(Token)match(input,67,FOLLOW_67_in_ora_lob998); 
            char_literal167_tree = (Object)adaptor.create(char_literal167);
            adaptor.addChild(root_0, char_literal167_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "ora_lob"

    public static class ora_lob_config_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ora_lob_config"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:180:1: ora_lob_config : ( ( 'TABLESPACE' name ) | ( 'ENABLE' 'STORAGE' 'IN' 'ROW' ) | ( 'CHUNK' INT ) | ( 'PCTVERSION' INT ) | ora_flag | ora_lob_index | ora_storage_spec );
    public final SQLParser.ora_lob_config_return ora_lob_config() throws RecognitionException {
        SQLParser.ora_lob_config_return retval = new SQLParser.ora_lob_config_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal168=null;
        Token string_literal170=null;
        Token string_literal171=null;
        Token string_literal172=null;
        Token string_literal173=null;
        Token string_literal174=null;
        Token INT175=null;
        Token string_literal176=null;
        Token INT177=null;
        SQLParser.name_return name169 = null;

        SQLParser.ora_flag_return ora_flag178 = null;

        SQLParser.ora_lob_index_return ora_lob_index179 = null;

        SQLParser.ora_storage_spec_return ora_storage_spec180 = null;


        Object string_literal168_tree=null;
        Object string_literal170_tree=null;
        Object string_literal171_tree=null;
        Object string_literal172_tree=null;
        Object string_literal173_tree=null;
        Object string_literal174_tree=null;
        Object INT175_tree=null;
        Object string_literal176_tree=null;
        Object INT177_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:181:2: ( ( 'TABLESPACE' name ) | ( 'ENABLE' 'STORAGE' 'IN' 'ROW' ) | ( 'CHUNK' INT ) | ( 'PCTVERSION' INT ) | ora_flag | ora_lob_index | ora_storage_spec )
            int alt24=7;
            switch ( input.LA(1) ) {
            case 76:
                {
                alt24=1;
                }
                break;
            case 82:
                {
                alt24=2;
                }
                break;
            case 119:
                {
                alt24=3;
                }
                break;
            case 120:
                {
                alt24=4;
                }
                break;
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
                {
                alt24=5;
                }
                break;
            case 121:
                {
                alt24=6;
                }
                break;
            case 90:
                {
                alt24=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:181:4: ( 'TABLESPACE' name )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:181:4: ( 'TABLESPACE' name )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:181:5: 'TABLESPACE' name
                    {
                    string_literal168=(Token)match(input,76,FOLLOW_76_in_ora_lob_config1007); 
                    string_literal168_tree = (Object)adaptor.create(string_literal168);
                    root_0 = (Object)adaptor.becomeRoot(string_literal168_tree, root_0);

                    pushFollow(FOLLOW_name_in_ora_lob_config1010);
                    name169=name();

                    state._fsp--;

                    adaptor.addChild(root_0, name169.getTree());

                    }


                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:182:4: ( 'ENABLE' 'STORAGE' 'IN' 'ROW' )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:182:4: ( 'ENABLE' 'STORAGE' 'IN' 'ROW' )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:182:5: 'ENABLE' 'STORAGE' 'IN' 'ROW'
                    {
                    string_literal170=(Token)match(input,82,FOLLOW_82_in_ora_lob_config1017); 
                    string_literal170_tree = (Object)adaptor.create(string_literal170);
                    adaptor.addChild(root_0, string_literal170_tree);

                    string_literal171=(Token)match(input,90,FOLLOW_90_in_ora_lob_config1019); 
                    string_literal171_tree = (Object)adaptor.create(string_literal171);
                    adaptor.addChild(root_0, string_literal171_tree);

                    string_literal172=(Token)match(input,IN,FOLLOW_IN_in_ora_lob_config1021); 
                    string_literal172_tree = (Object)adaptor.create(string_literal172);
                    adaptor.addChild(root_0, string_literal172_tree);

                    string_literal173=(Token)match(input,83,FOLLOW_83_in_ora_lob_config1023); 
                    string_literal173_tree = (Object)adaptor.create(string_literal173);
                    adaptor.addChild(root_0, string_literal173_tree);


                    }


                    }
                    break;
                case 3 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:183:4: ( 'CHUNK' INT )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:183:4: ( 'CHUNK' INT )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:183:5: 'CHUNK' INT
                    {
                    string_literal174=(Token)match(input,119,FOLLOW_119_in_ora_lob_config1030); 
                    string_literal174_tree = (Object)adaptor.create(string_literal174);
                    root_0 = (Object)adaptor.becomeRoot(string_literal174_tree, root_0);

                    INT175=(Token)match(input,INT,FOLLOW_INT_in_ora_lob_config1033); 
                    INT175_tree = (Object)adaptor.create(INT175);
                    adaptor.addChild(root_0, INT175_tree);


                    }


                    }
                    break;
                case 4 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:184:4: ( 'PCTVERSION' INT )
                    {
                    root_0 = (Object)adaptor.nil();

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:184:4: ( 'PCTVERSION' INT )
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:184:5: 'PCTVERSION' INT
                    {
                    string_literal176=(Token)match(input,120,FOLLOW_120_in_ora_lob_config1040); 
                    string_literal176_tree = (Object)adaptor.create(string_literal176);
                    root_0 = (Object)adaptor.becomeRoot(string_literal176_tree, root_0);

                    INT177=(Token)match(input,INT,FOLLOW_INT_in_ora_lob_config1043); 
                    INT177_tree = (Object)adaptor.create(INT177);
                    adaptor.addChild(root_0, INT177_tree);


                    }


                    }
                    break;
                case 5 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:185:4: ora_flag
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ora_flag_in_ora_lob_config1049);
                    ora_flag178=ora_flag();

                    state._fsp--;

                    adaptor.addChild(root_0, ora_flag178.getTree());

                    }
                    break;
                case 6 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:186:4: ora_lob_index
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ora_lob_index_in_ora_lob_config1054);
                    ora_lob_index179=ora_lob_index();

                    state._fsp--;

                    adaptor.addChild(root_0, ora_lob_index179.getTree());

                    }
                    break;
                case 7 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:187:4: ora_storage_spec
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ora_storage_spec_in_ora_lob_config1059);
                    ora_storage_spec180=ora_storage_spec();

                    state._fsp--;

                    adaptor.addChild(root_0, ora_storage_spec180.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "ora_lob_config"

    public static class ora_lob_index_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ora_lob_index"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:189:1: ora_lob_index : 'INDEX' '(' ora_configs ')' ;
    public final SQLParser.ora_lob_index_return ora_lob_index() throws RecognitionException {
        SQLParser.ora_lob_index_return retval = new SQLParser.ora_lob_index_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal181=null;
        Token char_literal182=null;
        Token char_literal184=null;
        SQLParser.ora_configs_return ora_configs183 = null;


        Object string_literal181_tree=null;
        Object char_literal182_tree=null;
        Object char_literal184_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:190:2: ( 'INDEX' '(' ora_configs ')' )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:190:4: 'INDEX' '(' ora_configs ')'
            {
            root_0 = (Object)adaptor.nil();

            string_literal181=(Token)match(input,121,FOLLOW_121_in_ora_lob_index1070); 
            string_literal181_tree = (Object)adaptor.create(string_literal181);
            adaptor.addChild(root_0, string_literal181_tree);

            char_literal182=(Token)match(input,66,FOLLOW_66_in_ora_lob_index1072); 
            char_literal182_tree = (Object)adaptor.create(char_literal182);
            adaptor.addChild(root_0, char_literal182_tree);

            pushFollow(FOLLOW_ora_configs_in_ora_lob_index1074);
            ora_configs183=ora_configs();

            state._fsp--;

            adaptor.addChild(root_0, ora_configs183.getTree());
            char_literal184=(Token)match(input,67,FOLLOW_67_in_ora_lob_index1076); 
            char_literal184_tree = (Object)adaptor.create(char_literal184);
            adaptor.addChild(root_0, char_literal184_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "ora_lob_index"

    public static class alter_table_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "alter_table"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:192:1: alter_table : 'ALTER' 'TABLE' table_name table_mutation -> ^( ALTER_TABLE table_name table_mutation ) ;
    public final SQLParser.alter_table_return alter_table() throws RecognitionException {
        SQLParser.alter_table_return retval = new SQLParser.alter_table_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal185=null;
        Token string_literal186=null;
        SQLParser.table_name_return table_name187 = null;

        SQLParser.table_mutation_return table_mutation188 = null;


        Object string_literal185_tree=null;
        Object string_literal186_tree=null;
        RewriteRuleTokenStream stream_122=new RewriteRuleTokenStream(adaptor,"token 122");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleSubtreeStream stream_table_mutation=new RewriteRuleSubtreeStream(adaptor,"rule table_mutation");
        RewriteRuleSubtreeStream stream_table_name=new RewriteRuleSubtreeStream(adaptor,"rule table_name");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:192:13: ( 'ALTER' 'TABLE' table_name table_mutation -> ^( ALTER_TABLE table_name table_mutation ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:192:15: 'ALTER' 'TABLE' table_name table_mutation
            {
            string_literal185=(Token)match(input,122,FOLLOW_122_in_alter_table1085);  
            stream_122.add(string_literal185);

            string_literal186=(Token)match(input,65,FOLLOW_65_in_alter_table1087);  
            stream_65.add(string_literal186);

            pushFollow(FOLLOW_table_name_in_alter_table1089);
            table_name187=table_name();

            state._fsp--;

            stream_table_name.add(table_name187.getTree());
            pushFollow(FOLLOW_table_mutation_in_alter_table1091);
            table_mutation188=table_mutation();

            state._fsp--;

            stream_table_mutation.add(table_mutation188.getTree());


            // AST REWRITE
            // elements: table_name, table_mutation
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 192:57: -> ^( ALTER_TABLE table_name table_mutation )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:192:60: ^( ALTER_TABLE table_name table_mutation )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ALTER_TABLE, "ALTER_TABLE"), root_1);

                adaptor.addChild(root_1, stream_table_name.nextTree());
                adaptor.addChild(root_1, stream_table_mutation.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "alter_table"

    public static class table_mutation_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "table_mutation"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:193:1: table_mutation : ( drop_pk | add_constraints );
    public final SQLParser.table_mutation_return table_mutation() throws RecognitionException {
        SQLParser.table_mutation_return retval = new SQLParser.table_mutation_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SQLParser.drop_pk_return drop_pk189 = null;

        SQLParser.add_constraints_return add_constraints190 = null;



        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:194:2: ( drop_pk | add_constraints )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==123) ) {
                alt25=1;
            }
            else if ( (LA25_0==125) ) {
                alt25=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:194:4: drop_pk
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_drop_pk_in_table_mutation1109);
                    drop_pk189=drop_pk();

                    state._fsp--;

                    adaptor.addChild(root_0, drop_pk189.getTree());

                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:195:4: add_constraints
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_add_constraints_in_table_mutation1114);
                    add_constraints190=add_constraints();

                    state._fsp--;

                    adaptor.addChild(root_0, add_constraints190.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "table_mutation"

    public static class drop_pk_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "drop_pk"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:197:1: drop_pk : 'DROP' 'PRIMARY' 'KEY' ( 'CASCADE' )? ;
    public final SQLParser.drop_pk_return drop_pk() throws RecognitionException {
        SQLParser.drop_pk_return retval = new SQLParser.drop_pk_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal191=null;
        Token string_literal192=null;
        Token string_literal193=null;
        Token string_literal194=null;

        Object string_literal191_tree=null;
        Object string_literal192_tree=null;
        Object string_literal193_tree=null;
        Object string_literal194_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:197:10: ( 'DROP' 'PRIMARY' 'KEY' ( 'CASCADE' )? )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:197:12: 'DROP' 'PRIMARY' 'KEY' ( 'CASCADE' )?
            {
            root_0 = (Object)adaptor.nil();

            string_literal191=(Token)match(input,123,FOLLOW_123_in_drop_pk1124); 
            string_literal191_tree = (Object)adaptor.create(string_literal191);
            adaptor.addChild(root_0, string_literal191_tree);

            string_literal192=(Token)match(input,PRIMARY,FOLLOW_PRIMARY_in_drop_pk1126); 
            string_literal192_tree = (Object)adaptor.create(string_literal192);
            adaptor.addChild(root_0, string_literal192_tree);

            string_literal193=(Token)match(input,74,FOLLOW_74_in_drop_pk1128); 
            string_literal193_tree = (Object)adaptor.create(string_literal193);
            adaptor.addChild(root_0, string_literal193_tree);

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:197:35: ( 'CASCADE' )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==124) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:197:35: 'CASCADE'
                    {
                    string_literal194=(Token)match(input,124,FOLLOW_124_in_drop_pk1130); 
                    string_literal194_tree = (Object)adaptor.create(string_literal194);
                    adaptor.addChild(root_0, string_literal194_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "drop_pk"

    public static class add_constraints_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "add_constraints"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:198:1: add_constraints : 'ADD' '(' constraint_spec ( ',' constraint_spec )* ')' ;
    public final SQLParser.add_constraints_return add_constraints() throws RecognitionException {
        SQLParser.add_constraints_return retval = new SQLParser.add_constraints_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal195=null;
        Token char_literal196=null;
        Token char_literal198=null;
        Token char_literal200=null;
        SQLParser.constraint_spec_return constraint_spec197 = null;

        SQLParser.constraint_spec_return constraint_spec199 = null;


        Object string_literal195_tree=null;
        Object char_literal196_tree=null;
        Object char_literal198_tree=null;
        Object char_literal200_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:198:17: ( 'ADD' '(' constraint_spec ( ',' constraint_spec )* ')' )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:198:19: 'ADD' '(' constraint_spec ( ',' constraint_spec )* ')'
            {
            root_0 = (Object)adaptor.nil();

            string_literal195=(Token)match(input,125,FOLLOW_125_in_add_constraints1138); 
            string_literal195_tree = (Object)adaptor.create(string_literal195);
            adaptor.addChild(root_0, string_literal195_tree);

            char_literal196=(Token)match(input,66,FOLLOW_66_in_add_constraints1140); 
            char_literal196_tree = (Object)adaptor.create(char_literal196);
            adaptor.addChild(root_0, char_literal196_tree);

            pushFollow(FOLLOW_constraint_spec_in_add_constraints1142);
            constraint_spec197=constraint_spec();

            state._fsp--;

            adaptor.addChild(root_0, constraint_spec197.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:198:45: ( ',' constraint_spec )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==68) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:198:46: ',' constraint_spec
            	    {
            	    char_literal198=(Token)match(input,68,FOLLOW_68_in_add_constraints1145); 
            	    char_literal198_tree = (Object)adaptor.create(char_literal198);
            	    adaptor.addChild(root_0, char_literal198_tree);

            	    pushFollow(FOLLOW_constraint_spec_in_add_constraints1147);
            	    constraint_spec199=constraint_spec();

            	    state._fsp--;

            	    adaptor.addChild(root_0, constraint_spec199.getTree());

            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

            char_literal200=(Token)match(input,67,FOLLOW_67_in_add_constraints1151); 
            char_literal200_tree = (Object)adaptor.create(char_literal200);
            adaptor.addChild(root_0, char_literal200_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "add_constraints"

    public static class constraint_spec_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "constraint_spec"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:199:1: constraint_spec : ( 'CONSTRAINT' name )? constraint_detail ;
    public final SQLParser.constraint_spec_return constraint_spec() throws RecognitionException {
        SQLParser.constraint_spec_return retval = new SQLParser.constraint_spec_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal201=null;
        SQLParser.name_return name202 = null;

        SQLParser.constraint_detail_return constraint_detail203 = null;


        Object string_literal201_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:199:17: ( ( 'CONSTRAINT' name )? constraint_detail )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:199:19: ( 'CONSTRAINT' name )? constraint_detail
            {
            root_0 = (Object)adaptor.nil();

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:199:19: ( 'CONSTRAINT' name )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==73) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:199:20: 'CONSTRAINT' name
                    {
                    string_literal201=(Token)match(input,73,FOLLOW_73_in_constraint_spec1159); 
                    string_literal201_tree = (Object)adaptor.create(string_literal201);
                    adaptor.addChild(root_0, string_literal201_tree);

                    pushFollow(FOLLOW_name_in_constraint_spec1161);
                    name202=name();

                    state._fsp--;

                    adaptor.addChild(root_0, name202.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_constraint_detail_in_constraint_spec1165);
            constraint_detail203=constraint_detail();

            state._fsp--;

            adaptor.addChild(root_0, constraint_detail203.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "constraint_spec"

    public static class constraint_detail_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "constraint_detail"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:200:1: constraint_detail : ( primary_key_constraint | foreign_key_constraint | unique_constraint | check_constraint );
    public final SQLParser.constraint_detail_return constraint_detail() throws RecognitionException {
        SQLParser.constraint_detail_return retval = new SQLParser.constraint_detail_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SQLParser.primary_key_constraint_return primary_key_constraint204 = null;

        SQLParser.foreign_key_constraint_return foreign_key_constraint205 = null;

        SQLParser.unique_constraint_return unique_constraint206 = null;

        SQLParser.check_constraint_return check_constraint207 = null;



        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:201:2: ( primary_key_constraint | foreign_key_constraint | unique_constraint | check_constraint )
            int alt29=4;
            switch ( input.LA(1) ) {
            case PRIMARY:
                {
                alt29=1;
                }
                break;
            case 127:
                {
                alt29=2;
                }
                break;
            case 129:
                {
                alt29=3;
                }
                break;
            case 130:
                {
                alt29=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }

            switch (alt29) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:201:4: primary_key_constraint
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_primary_key_constraint_in_constraint_detail1173);
                    primary_key_constraint204=primary_key_constraint();

                    state._fsp--;

                    adaptor.addChild(root_0, primary_key_constraint204.getTree());

                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:202:4: foreign_key_constraint
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_foreign_key_constraint_in_constraint_detail1178);
                    foreign_key_constraint205=foreign_key_constraint();

                    state._fsp--;

                    adaptor.addChild(root_0, foreign_key_constraint205.getTree());

                    }
                    break;
                case 3 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:203:4: unique_constraint
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_unique_constraint_in_constraint_detail1183);
                    unique_constraint206=unique_constraint();

                    state._fsp--;

                    adaptor.addChild(root_0, unique_constraint206.getTree());

                    }
                    break;
                case 4 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:204:4: check_constraint
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_check_constraint_in_constraint_detail1188);
                    check_constraint207=check_constraint();

                    state._fsp--;

                    adaptor.addChild(root_0, check_constraint207.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "constraint_detail"

    public static class primary_key_constraint_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primary_key_constraint"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:205:1: primary_key_constraint : 'PRIMARY' 'KEY' '(' name_list ')' ( 'USING' 'INDEX' ora_configs )? ;
    public final SQLParser.primary_key_constraint_return primary_key_constraint() throws RecognitionException {
        SQLParser.primary_key_constraint_return retval = new SQLParser.primary_key_constraint_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal208=null;
        Token string_literal209=null;
        Token char_literal210=null;
        Token char_literal212=null;
        Token string_literal213=null;
        Token string_literal214=null;
        SQLParser.name_list_return name_list211 = null;

        SQLParser.ora_configs_return ora_configs215 = null;


        Object string_literal208_tree=null;
        Object string_literal209_tree=null;
        Object char_literal210_tree=null;
        Object char_literal212_tree=null;
        Object string_literal213_tree=null;
        Object string_literal214_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:205:24: ( 'PRIMARY' 'KEY' '(' name_list ')' ( 'USING' 'INDEX' ora_configs )? )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:205:26: 'PRIMARY' 'KEY' '(' name_list ')' ( 'USING' 'INDEX' ora_configs )?
            {
            root_0 = (Object)adaptor.nil();

            string_literal208=(Token)match(input,PRIMARY,FOLLOW_PRIMARY_in_primary_key_constraint1195); 
            string_literal208_tree = (Object)adaptor.create(string_literal208);
            adaptor.addChild(root_0, string_literal208_tree);

            string_literal209=(Token)match(input,74,FOLLOW_74_in_primary_key_constraint1197); 
            string_literal209_tree = (Object)adaptor.create(string_literal209);
            adaptor.addChild(root_0, string_literal209_tree);

            char_literal210=(Token)match(input,66,FOLLOW_66_in_primary_key_constraint1199); 
            char_literal210_tree = (Object)adaptor.create(char_literal210);
            adaptor.addChild(root_0, char_literal210_tree);

            pushFollow(FOLLOW_name_list_in_primary_key_constraint1201);
            name_list211=name_list();

            state._fsp--;

            adaptor.addChild(root_0, name_list211.getTree());
            char_literal212=(Token)match(input,67,FOLLOW_67_in_primary_key_constraint1203); 
            char_literal212_tree = (Object)adaptor.create(char_literal212);
            adaptor.addChild(root_0, char_literal212_tree);

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:205:60: ( 'USING' 'INDEX' ora_configs )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==126) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:205:61: 'USING' 'INDEX' ora_configs
                    {
                    string_literal213=(Token)match(input,126,FOLLOW_126_in_primary_key_constraint1206); 
                    string_literal213_tree = (Object)adaptor.create(string_literal213);
                    adaptor.addChild(root_0, string_literal213_tree);

                    string_literal214=(Token)match(input,121,FOLLOW_121_in_primary_key_constraint1208); 
                    string_literal214_tree = (Object)adaptor.create(string_literal214);
                    adaptor.addChild(root_0, string_literal214_tree);

                    pushFollow(FOLLOW_ora_configs_in_primary_key_constraint1210);
                    ora_configs215=ora_configs();

                    state._fsp--;

                    adaptor.addChild(root_0, ora_configs215.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "primary_key_constraint"

    public static class foreign_key_constraint_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "foreign_key_constraint"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:206:1: foreign_key_constraint : 'FOREIGN' 'KEY' '(' name_list ')' 'REFERENCES' table_name ( '(' )+ ( name_list )+ ')' ;
    public final SQLParser.foreign_key_constraint_return foreign_key_constraint() throws RecognitionException {
        SQLParser.foreign_key_constraint_return retval = new SQLParser.foreign_key_constraint_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal216=null;
        Token string_literal217=null;
        Token char_literal218=null;
        Token char_literal220=null;
        Token string_literal221=null;
        Token char_literal223=null;
        Token char_literal225=null;
        SQLParser.name_list_return name_list219 = null;

        SQLParser.table_name_return table_name222 = null;

        SQLParser.name_list_return name_list224 = null;


        Object string_literal216_tree=null;
        Object string_literal217_tree=null;
        Object char_literal218_tree=null;
        Object char_literal220_tree=null;
        Object string_literal221_tree=null;
        Object char_literal223_tree=null;
        Object char_literal225_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:206:24: ( 'FOREIGN' 'KEY' '(' name_list ')' 'REFERENCES' table_name ( '(' )+ ( name_list )+ ')' )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:206:26: 'FOREIGN' 'KEY' '(' name_list ')' 'REFERENCES' table_name ( '(' )+ ( name_list )+ ')'
            {
            root_0 = (Object)adaptor.nil();

            string_literal216=(Token)match(input,127,FOLLOW_127_in_foreign_key_constraint1219); 
            string_literal216_tree = (Object)adaptor.create(string_literal216);
            adaptor.addChild(root_0, string_literal216_tree);

            string_literal217=(Token)match(input,74,FOLLOW_74_in_foreign_key_constraint1221); 
            string_literal217_tree = (Object)adaptor.create(string_literal217);
            adaptor.addChild(root_0, string_literal217_tree);

            char_literal218=(Token)match(input,66,FOLLOW_66_in_foreign_key_constraint1223); 
            char_literal218_tree = (Object)adaptor.create(char_literal218);
            adaptor.addChild(root_0, char_literal218_tree);

            pushFollow(FOLLOW_name_list_in_foreign_key_constraint1225);
            name_list219=name_list();

            state._fsp--;

            adaptor.addChild(root_0, name_list219.getTree());
            char_literal220=(Token)match(input,67,FOLLOW_67_in_foreign_key_constraint1227); 
            char_literal220_tree = (Object)adaptor.create(char_literal220);
            adaptor.addChild(root_0, char_literal220_tree);

            string_literal221=(Token)match(input,128,FOLLOW_128_in_foreign_key_constraint1229); 
            string_literal221_tree = (Object)adaptor.create(string_literal221);
            adaptor.addChild(root_0, string_literal221_tree);

            pushFollow(FOLLOW_table_name_in_foreign_key_constraint1231);
            table_name222=table_name();

            state._fsp--;

            adaptor.addChild(root_0, table_name222.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:206:84: ( '(' )+
            int cnt31=0;
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==66) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:206:84: '('
            	    {
            	    char_literal223=(Token)match(input,66,FOLLOW_66_in_foreign_key_constraint1233); 
            	    char_literal223_tree = (Object)adaptor.create(char_literal223);
            	    adaptor.addChild(root_0, char_literal223_tree);


            	    }
            	    break;

            	default :
            	    if ( cnt31 >= 1 ) break loop31;
                        EarlyExitException eee =
                            new EarlyExitException(31, input);
                        throw eee;
                }
                cnt31++;
            } while (true);

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:206:90: ( name_list )+
            int cnt32=0;
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==IDENTIFIER||(LA32_0>=QUOTED_NAME && LA32_0<=COMMENT)||LA32_0==IS||LA32_0==62||LA32_0==65||LA32_0==74||(LA32_0>=76 && LA32_0<=80)||LA32_0==86||LA32_0==90||LA32_0==98||(LA32_0>=109 && LA32_0<=116)||(LA32_0>=121 && LA32_0<=123)||LA32_0==129||LA32_0==133||LA32_0==135||LA32_0==137) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:206:90: name_list
            	    {
            	    pushFollow(FOLLOW_name_list_in_foreign_key_constraint1237);
            	    name_list224=name_list();

            	    state._fsp--;

            	    adaptor.addChild(root_0, name_list224.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt32 >= 1 ) break loop32;
                        EarlyExitException eee =
                            new EarlyExitException(32, input);
                        throw eee;
                }
                cnt32++;
            } while (true);

            char_literal225=(Token)match(input,67,FOLLOW_67_in_foreign_key_constraint1241); 
            char_literal225_tree = (Object)adaptor.create(char_literal225);
            adaptor.addChild(root_0, char_literal225_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "foreign_key_constraint"

    public static class unique_constraint_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unique_constraint"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:207:1: unique_constraint : 'UNIQUE' '(' name_list ')' ( 'USING' 'INDEX' ora_configs )? ;
    public final SQLParser.unique_constraint_return unique_constraint() throws RecognitionException {
        SQLParser.unique_constraint_return retval = new SQLParser.unique_constraint_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal226=null;
        Token char_literal227=null;
        Token char_literal229=null;
        Token string_literal230=null;
        Token string_literal231=null;
        SQLParser.name_list_return name_list228 = null;

        SQLParser.ora_configs_return ora_configs232 = null;


        Object string_literal226_tree=null;
        Object char_literal227_tree=null;
        Object char_literal229_tree=null;
        Object string_literal230_tree=null;
        Object string_literal231_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:207:19: ( 'UNIQUE' '(' name_list ')' ( 'USING' 'INDEX' ora_configs )? )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:207:21: 'UNIQUE' '(' name_list ')' ( 'USING' 'INDEX' ora_configs )?
            {
            root_0 = (Object)adaptor.nil();

            string_literal226=(Token)match(input,129,FOLLOW_129_in_unique_constraint1248); 
            string_literal226_tree = (Object)adaptor.create(string_literal226);
            adaptor.addChild(root_0, string_literal226_tree);

            char_literal227=(Token)match(input,66,FOLLOW_66_in_unique_constraint1250); 
            char_literal227_tree = (Object)adaptor.create(char_literal227);
            adaptor.addChild(root_0, char_literal227_tree);

            pushFollow(FOLLOW_name_list_in_unique_constraint1252);
            name_list228=name_list();

            state._fsp--;

            adaptor.addChild(root_0, name_list228.getTree());
            char_literal229=(Token)match(input,67,FOLLOW_67_in_unique_constraint1254); 
            char_literal229_tree = (Object)adaptor.create(char_literal229);
            adaptor.addChild(root_0, char_literal229_tree);

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:207:48: ( 'USING' 'INDEX' ora_configs )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==126) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:207:49: 'USING' 'INDEX' ora_configs
                    {
                    string_literal230=(Token)match(input,126,FOLLOW_126_in_unique_constraint1257); 
                    string_literal230_tree = (Object)adaptor.create(string_literal230);
                    adaptor.addChild(root_0, string_literal230_tree);

                    string_literal231=(Token)match(input,121,FOLLOW_121_in_unique_constraint1259); 
                    string_literal231_tree = (Object)adaptor.create(string_literal231);
                    adaptor.addChild(root_0, string_literal231_tree);

                    pushFollow(FOLLOW_ora_configs_in_unique_constraint1261);
                    ora_configs232=ora_configs();

                    state._fsp--;

                    adaptor.addChild(root_0, ora_configs232.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "unique_constraint"

    public static class check_constraint_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "check_constraint"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:208:1: check_constraint : 'CHECK' expression ;
    public final SQLParser.check_constraint_return check_constraint() throws RecognitionException {
        SQLParser.check_constraint_return retval = new SQLParser.check_constraint_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal233=null;
        SQLParser.expression_return expression234 = null;


        Object string_literal233_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:208:18: ( 'CHECK' expression )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:208:20: 'CHECK' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal233=(Token)match(input,130,FOLLOW_130_in_check_constraint1270); 
            string_literal233_tree = (Object)adaptor.create(string_literal233);
            adaptor.addChild(root_0, string_literal233_tree);

            pushFollow(FOLLOW_expression_in_check_constraint1272);
            expression234=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression234.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "check_constraint"

    public static class drop_table_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "drop_table"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:212:1: drop_table : 'DROP' 'TABLE' table_name ( 'CASCADE' 'CONSTRAINTS' )? -> ^( DROP_TABLE table_name ) ;
    public final SQLParser.drop_table_return drop_table() throws RecognitionException {
        SQLParser.drop_table_return retval = new SQLParser.drop_table_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal235=null;
        Token string_literal236=null;
        Token string_literal238=null;
        Token string_literal239=null;
        SQLParser.table_name_return table_name237 = null;


        Object string_literal235_tree=null;
        Object string_literal236_tree=null;
        Object string_literal238_tree=null;
        Object string_literal239_tree=null;
        RewriteRuleTokenStream stream_123=new RewriteRuleTokenStream(adaptor,"token 123");
        RewriteRuleTokenStream stream_124=new RewriteRuleTokenStream(adaptor,"token 124");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_131=new RewriteRuleTokenStream(adaptor,"token 131");
        RewriteRuleSubtreeStream stream_table_name=new RewriteRuleSubtreeStream(adaptor,"rule table_name");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:212:12: ( 'DROP' 'TABLE' table_name ( 'CASCADE' 'CONSTRAINTS' )? -> ^( DROP_TABLE table_name ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:212:14: 'DROP' 'TABLE' table_name ( 'CASCADE' 'CONSTRAINTS' )?
            {
            string_literal235=(Token)match(input,123,FOLLOW_123_in_drop_table1283);  
            stream_123.add(string_literal235);

            string_literal236=(Token)match(input,65,FOLLOW_65_in_drop_table1285);  
            stream_65.add(string_literal236);

            pushFollow(FOLLOW_table_name_in_drop_table1287);
            table_name237=table_name();

            state._fsp--;

            stream_table_name.add(table_name237.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:212:40: ( 'CASCADE' 'CONSTRAINTS' )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==124) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:212:41: 'CASCADE' 'CONSTRAINTS'
                    {
                    string_literal238=(Token)match(input,124,FOLLOW_124_in_drop_table1290);  
                    stream_124.add(string_literal238);

                    string_literal239=(Token)match(input,131,FOLLOW_131_in_drop_table1292);  
                    stream_131.add(string_literal239);


                    }
                    break;

            }



            // AST REWRITE
            // elements: table_name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 212:67: -> ^( DROP_TABLE table_name )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:212:70: ^( DROP_TABLE table_name )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DROP_TABLE, "DROP_TABLE"), root_1);

                adaptor.addChild(root_1, stream_table_name.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "drop_table"

    public static class create_index_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "create_index"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:216:1: create_index : 'CREATE' ( 'UNIQUE' )? 'INDEX' index_name 'ON' table_name '(' name_list ')' ( 'INDEXTYPE' 'IS' qualified_name )? ora_configs -> ^( CREATE_INDEX index_name ) ;
    public final SQLParser.create_index_return create_index() throws RecognitionException {
        SQLParser.create_index_return retval = new SQLParser.create_index_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal240=null;
        Token string_literal241=null;
        Token string_literal242=null;
        Token string_literal244=null;
        Token char_literal246=null;
        Token char_literal248=null;
        Token string_literal249=null;
        Token string_literal250=null;
        SQLParser.index_name_return index_name243 = null;

        SQLParser.table_name_return table_name245 = null;

        SQLParser.name_list_return name_list247 = null;

        SQLParser.qualified_name_return qualified_name251 = null;

        SQLParser.ora_configs_return ora_configs252 = null;


        Object string_literal240_tree=null;
        Object string_literal241_tree=null;
        Object string_literal242_tree=null;
        Object string_literal244_tree=null;
        Object char_literal246_tree=null;
        Object char_literal248_tree=null;
        Object string_literal249_tree=null;
        Object string_literal250_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_132=new RewriteRuleTokenStream(adaptor,"token 132");
        RewriteRuleTokenStream stream_121=new RewriteRuleTokenStream(adaptor,"token 121");
        RewriteRuleTokenStream stream_IS=new RewriteRuleTokenStream(adaptor,"token IS");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_129=new RewriteRuleTokenStream(adaptor,"token 129");
        RewriteRuleSubtreeStream stream_index_name=new RewriteRuleSubtreeStream(adaptor,"rule index_name");
        RewriteRuleSubtreeStream stream_ora_configs=new RewriteRuleSubtreeStream(adaptor,"rule ora_configs");
        RewriteRuleSubtreeStream stream_table_name=new RewriteRuleSubtreeStream(adaptor,"rule table_name");
        RewriteRuleSubtreeStream stream_name_list=new RewriteRuleSubtreeStream(adaptor,"rule name_list");
        RewriteRuleSubtreeStream stream_qualified_name=new RewriteRuleSubtreeStream(adaptor,"rule qualified_name");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:216:14: ( 'CREATE' ( 'UNIQUE' )? 'INDEX' index_name 'ON' table_name '(' name_list ')' ( 'INDEXTYPE' 'IS' qualified_name )? ora_configs -> ^( CREATE_INDEX index_name ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:216:16: 'CREATE' ( 'UNIQUE' )? 'INDEX' index_name 'ON' table_name '(' name_list ')' ( 'INDEXTYPE' 'IS' qualified_name )? ora_configs
            {
            string_literal240=(Token)match(input,62,FOLLOW_62_in_create_index1312);  
            stream_62.add(string_literal240);

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:216:25: ( 'UNIQUE' )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==129) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:216:25: 'UNIQUE'
                    {
                    string_literal241=(Token)match(input,129,FOLLOW_129_in_create_index1314);  
                    stream_129.add(string_literal241);


                    }
                    break;

            }

            string_literal242=(Token)match(input,121,FOLLOW_121_in_create_index1317);  
            stream_121.add(string_literal242);

            pushFollow(FOLLOW_index_name_in_create_index1319);
            index_name243=index_name();

            state._fsp--;

            stream_index_name.add(index_name243.getTree());
            string_literal244=(Token)match(input,86,FOLLOW_86_in_create_index1321);  
            stream_86.add(string_literal244);

            pushFollow(FOLLOW_table_name_in_create_index1323);
            table_name245=table_name();

            state._fsp--;

            stream_table_name.add(table_name245.getTree());
            char_literal246=(Token)match(input,66,FOLLOW_66_in_create_index1325);  
            stream_66.add(char_literal246);

            pushFollow(FOLLOW_name_list_in_create_index1327);
            name_list247=name_list();

            state._fsp--;

            stream_name_list.add(name_list247.getTree());
            char_literal248=(Token)match(input,67,FOLLOW_67_in_create_index1329);  
            stream_67.add(char_literal248);

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:216:88: ( 'INDEXTYPE' 'IS' qualified_name )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==132) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:216:89: 'INDEXTYPE' 'IS' qualified_name
                    {
                    string_literal249=(Token)match(input,132,FOLLOW_132_in_create_index1332);  
                    stream_132.add(string_literal249);

                    string_literal250=(Token)match(input,IS,FOLLOW_IS_in_create_index1334);  
                    stream_IS.add(string_literal250);

                    pushFollow(FOLLOW_qualified_name_in_create_index1336);
                    qualified_name251=qualified_name();

                    state._fsp--;

                    stream_qualified_name.add(qualified_name251.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_ora_configs_in_create_index1340);
            ora_configs252=ora_configs();

            state._fsp--;

            stream_ora_configs.add(ora_configs252.getTree());


            // AST REWRITE
            // elements: index_name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 216:135: -> ^( CREATE_INDEX index_name )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:216:138: ^( CREATE_INDEX index_name )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CREATE_INDEX, "CREATE_INDEX"), root_1);

                adaptor.addChild(root_1, stream_index_name.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "create_index"

    public static class index_name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "index_name"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:217:1: index_name : qualified_name -> ^( INDEX_NAME qualified_name ) ;
    public final SQLParser.index_name_return index_name() throws RecognitionException {
        SQLParser.index_name_return retval = new SQLParser.index_name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SQLParser.qualified_name_return qualified_name253 = null;


        RewriteRuleSubtreeStream stream_qualified_name=new RewriteRuleSubtreeStream(adaptor,"rule qualified_name");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:217:12: ( qualified_name -> ^( INDEX_NAME qualified_name ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:217:14: qualified_name
            {
            pushFollow(FOLLOW_qualified_name_in_index_name1355);
            qualified_name253=qualified_name();

            state._fsp--;

            stream_qualified_name.add(qualified_name253.getTree());


            // AST REWRITE
            // elements: qualified_name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 217:29: -> ^( INDEX_NAME qualified_name )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:217:32: ^( INDEX_NAME qualified_name )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INDEX_NAME, "INDEX_NAME"), root_1);

                adaptor.addChild(root_1, stream_qualified_name.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "index_name"

    public static class drop_index_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "drop_index"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:221:1: drop_index : 'DROP' 'INDEX' index_name ;
    public final SQLParser.drop_index_return drop_index() throws RecognitionException {
        SQLParser.drop_index_return retval = new SQLParser.drop_index_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal254=null;
        Token string_literal255=null;
        SQLParser.index_name_return index_name256 = null;


        Object string_literal254_tree=null;
        Object string_literal255_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:221:12: ( 'DROP' 'INDEX' index_name )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:221:14: 'DROP' 'INDEX' index_name
            {
            root_0 = (Object)adaptor.nil();

            string_literal254=(Token)match(input,123,FOLLOW_123_in_drop_index1373); 
            string_literal254_tree = (Object)adaptor.create(string_literal254);
            adaptor.addChild(root_0, string_literal254_tree);

            string_literal255=(Token)match(input,121,FOLLOW_121_in_drop_index1375); 
            string_literal255_tree = (Object)adaptor.create(string_literal255);
            adaptor.addChild(root_0, string_literal255_tree);

            pushFollow(FOLLOW_index_name_in_drop_index1377);
            index_name256=index_name();

            state._fsp--;

            adaptor.addChild(root_0, index_name256.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "drop_index"

    public static class create_sequence_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "create_sequence"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:224:1: create_sequence : 'CREATE' 'SEQUENCE' name -> ^( CREATE_SEQUENCE name ) ;
    public final SQLParser.create_sequence_return create_sequence() throws RecognitionException {
        SQLParser.create_sequence_return retval = new SQLParser.create_sequence_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal257=null;
        Token string_literal258=null;
        SQLParser.name_return name259 = null;


        Object string_literal257_tree=null;
        Object string_literal258_tree=null;
        RewriteRuleTokenStream stream_133=new RewriteRuleTokenStream(adaptor,"token 133");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:224:17: ( 'CREATE' 'SEQUENCE' name -> ^( CREATE_SEQUENCE name ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:224:19: 'CREATE' 'SEQUENCE' name
            {
            string_literal257=(Token)match(input,62,FOLLOW_62_in_create_sequence1386);  
            stream_62.add(string_literal257);

            string_literal258=(Token)match(input,133,FOLLOW_133_in_create_sequence1388);  
            stream_133.add(string_literal258);

            pushFollow(FOLLOW_name_in_create_sequence1390);
            name259=name();

            state._fsp--;

            stream_name.add(name259.getTree());


            // AST REWRITE
            // elements: name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 224:44: -> ^( CREATE_SEQUENCE name )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:224:47: ^( CREATE_SEQUENCE name )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CREATE_SEQUENCE, "CREATE_SEQUENCE"), root_1);

                adaptor.addChild(root_1, stream_name.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "create_sequence"

    public static class drop_sequence_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "drop_sequence"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:225:1: drop_sequence : 'DROP' 'SEQUENCE' name -> ^( DROP_SEQUENCE name ) ;
    public final SQLParser.drop_sequence_return drop_sequence() throws RecognitionException {
        SQLParser.drop_sequence_return retval = new SQLParser.drop_sequence_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal260=null;
        Token string_literal261=null;
        SQLParser.name_return name262 = null;


        Object string_literal260_tree=null;
        Object string_literal261_tree=null;
        RewriteRuleTokenStream stream_133=new RewriteRuleTokenStream(adaptor,"token 133");
        RewriteRuleTokenStream stream_123=new RewriteRuleTokenStream(adaptor,"token 123");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:225:15: ( 'DROP' 'SEQUENCE' name -> ^( DROP_SEQUENCE name ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:225:17: 'DROP' 'SEQUENCE' name
            {
            string_literal260=(Token)match(input,123,FOLLOW_123_in_drop_sequence1405);  
            stream_123.add(string_literal260);

            string_literal261=(Token)match(input,133,FOLLOW_133_in_drop_sequence1407);  
            stream_133.add(string_literal261);

            pushFollow(FOLLOW_name_in_drop_sequence1409);
            name262=name();

            state._fsp--;

            stream_name.add(name262.getTree());


            // AST REWRITE
            // elements: name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 225:40: -> ^( DROP_SEQUENCE name )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:225:43: ^( DROP_SEQUENCE name )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DROP_SEQUENCE, "DROP_SEQUENCE"), root_1);

                adaptor.addChild(root_1, stream_name.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "drop_sequence"

    public static class comment_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "comment"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:228:1: comment : 'COMMENT' 'ON' ( table_comment_details | column_comment_details ) ;
    public final SQLParser.comment_return comment() throws RecognitionException {
        SQLParser.comment_return retval = new SQLParser.comment_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal263=null;
        Token string_literal264=null;
        SQLParser.table_comment_details_return table_comment_details265 = null;

        SQLParser.column_comment_details_return column_comment_details266 = null;


        Object string_literal263_tree=null;
        Object string_literal264_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:228:10: ( 'COMMENT' 'ON' ( table_comment_details | column_comment_details ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:228:12: 'COMMENT' 'ON' ( table_comment_details | column_comment_details )
            {
            root_0 = (Object)adaptor.nil();

            string_literal263=(Token)match(input,134,FOLLOW_134_in_comment1427); 
            string_literal264=(Token)match(input,86,FOLLOW_86_in_comment1430); 
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:228:29: ( table_comment_details | column_comment_details )
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==65) ) {
                alt37=1;
            }
            else if ( (LA37_0==135) ) {
                alt37=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;
            }
            switch (alt37) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:228:30: table_comment_details
                    {
                    pushFollow(FOLLOW_table_comment_details_in_comment1434);
                    table_comment_details265=table_comment_details();

                    state._fsp--;

                    adaptor.addChild(root_0, table_comment_details265.getTree());

                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:228:54: column_comment_details
                    {
                    pushFollow(FOLLOW_column_comment_details_in_comment1438);
                    column_comment_details266=column_comment_details();

                    state._fsp--;

                    adaptor.addChild(root_0, column_comment_details266.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "comment"

    public static class table_comment_details_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "table_comment_details"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:229:1: table_comment_details : 'TABLE' table_name 'IS' STRING -> ^( COMMENT_TABLE table_name STRING ) ;
    public final SQLParser.table_comment_details_return table_comment_details() throws RecognitionException {
        SQLParser.table_comment_details_return retval = new SQLParser.table_comment_details_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal267=null;
        Token string_literal269=null;
        Token STRING270=null;
        SQLParser.table_name_return table_name268 = null;


        Object string_literal267_tree=null;
        Object string_literal269_tree=null;
        Object STRING270_tree=null;
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_IS=new RewriteRuleTokenStream(adaptor,"token IS");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_table_name=new RewriteRuleSubtreeStream(adaptor,"rule table_name");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:230:3: ( 'TABLE' table_name 'IS' STRING -> ^( COMMENT_TABLE table_name STRING ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:230:5: 'TABLE' table_name 'IS' STRING
            {
            string_literal267=(Token)match(input,65,FOLLOW_65_in_table_comment_details1448);  
            stream_65.add(string_literal267);

            pushFollow(FOLLOW_table_name_in_table_comment_details1450);
            table_name268=table_name();

            state._fsp--;

            stream_table_name.add(table_name268.getTree());
            string_literal269=(Token)match(input,IS,FOLLOW_IS_in_table_comment_details1452);  
            stream_IS.add(string_literal269);

            STRING270=(Token)match(input,STRING,FOLLOW_STRING_in_table_comment_details1454);  
            stream_STRING.add(STRING270);



            // AST REWRITE
            // elements: table_name, STRING
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 230:36: -> ^( COMMENT_TABLE table_name STRING )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:230:39: ^( COMMENT_TABLE table_name STRING )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMMENT_TABLE, "COMMENT_TABLE"), root_1);

                adaptor.addChild(root_1, stream_table_name.nextTree());
                adaptor.addChild(root_1, stream_STRING.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "table_comment_details"

    public static class column_comment_details_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_comment_details"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:231:1: column_comment_details : 'COLUMN' column_name 'IS' STRING -> ^( COMMENT_COLUMN column_name STRING ) ;
    public final SQLParser.column_comment_details_return column_comment_details() throws RecognitionException {
        SQLParser.column_comment_details_return retval = new SQLParser.column_comment_details_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal271=null;
        Token string_literal273=null;
        Token STRING274=null;
        SQLParser.column_name_return column_name272 = null;


        Object string_literal271_tree=null;
        Object string_literal273_tree=null;
        Object STRING274_tree=null;
        RewriteRuleTokenStream stream_135=new RewriteRuleTokenStream(adaptor,"token 135");
        RewriteRuleTokenStream stream_IS=new RewriteRuleTokenStream(adaptor,"token IS");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_column_name=new RewriteRuleSubtreeStream(adaptor,"rule column_name");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:232:3: ( 'COLUMN' column_name 'IS' STRING -> ^( COMMENT_COLUMN column_name STRING ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:232:5: 'COLUMN' column_name 'IS' STRING
            {
            string_literal271=(Token)match(input,135,FOLLOW_135_in_column_comment_details1473);  
            stream_135.add(string_literal271);

            pushFollow(FOLLOW_column_name_in_column_comment_details1475);
            column_name272=column_name();

            state._fsp--;

            stream_column_name.add(column_name272.getTree());
            string_literal273=(Token)match(input,IS,FOLLOW_IS_in_column_comment_details1477);  
            stream_IS.add(string_literal273);

            STRING274=(Token)match(input,STRING,FOLLOW_STRING_in_column_comment_details1479);  
            stream_STRING.add(STRING274);



            // AST REWRITE
            // elements: column_name, STRING
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 232:38: -> ^( COMMENT_COLUMN column_name STRING )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:232:41: ^( COMMENT_COLUMN column_name STRING )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMMENT_COLUMN, "COMMENT_COLUMN"), root_1);

                adaptor.addChild(root_1, stream_column_name.nextTree());
                adaptor.addChild(root_1, stream_STRING.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_comment_details"

    public static class column_name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "column_name"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:233:1: column_name : qualified_name -> ^( COLUMN_NAME qualified_name ) ;
    public final SQLParser.column_name_return column_name() throws RecognitionException {
        SQLParser.column_name_return retval = new SQLParser.column_name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SQLParser.qualified_name_return qualified_name275 = null;


        RewriteRuleSubtreeStream stream_qualified_name=new RewriteRuleSubtreeStream(adaptor,"rule qualified_name");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:233:13: ( qualified_name -> ^( COLUMN_NAME qualified_name ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:233:15: qualified_name
            {
            pushFollow(FOLLOW_qualified_name_in_column_name1496);
            qualified_name275=qualified_name();

            state._fsp--;

            stream_qualified_name.add(qualified_name275.getTree());


            // AST REWRITE
            // elements: qualified_name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 233:30: -> ^( COLUMN_NAME qualified_name )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:233:33: ^( COLUMN_NAME qualified_name )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COLUMN_NAME, "COLUMN_NAME"), root_1);

                adaptor.addChild(root_1, stream_qualified_name.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "column_name"

    public static class expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:236:1: expression : and_expression ( 'OR' and_expression )* ;
    public final SQLParser.expression_return expression() throws RecognitionException {
        SQLParser.expression_return retval = new SQLParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal277=null;
        SQLParser.and_expression_return and_expression276 = null;

        SQLParser.and_expression_return and_expression278 = null;


        Object string_literal277_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:236:13: ( and_expression ( 'OR' and_expression )* )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:236:15: and_expression ( 'OR' and_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_and_expression_in_expression1514);
            and_expression276=and_expression();

            state._fsp--;

            adaptor.addChild(root_0, and_expression276.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:236:30: ( 'OR' and_expression )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==OR) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:236:31: 'OR' and_expression
            	    {
            	    string_literal277=(Token)match(input,OR,FOLLOW_OR_in_expression1517); 
            	    string_literal277_tree = (Object)adaptor.create(string_literal277);
            	    root_0 = (Object)adaptor.becomeRoot(string_literal277_tree, root_0);

            	    pushFollow(FOLLOW_and_expression_in_expression1520);
            	    and_expression278=and_expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, and_expression278.getTree());

            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "expression"

    public static class and_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "and_expression"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:237:1: and_expression : exclusive_or_expression ( 'AND' exclusive_or_expression )* ;
    public final SQLParser.and_expression_return and_expression() throws RecognitionException {
        SQLParser.and_expression_return retval = new SQLParser.and_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal280=null;
        SQLParser.exclusive_or_expression_return exclusive_or_expression279 = null;

        SQLParser.exclusive_or_expression_return exclusive_or_expression281 = null;


        Object string_literal280_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:237:17: ( exclusive_or_expression ( 'AND' exclusive_or_expression )* )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:237:19: exclusive_or_expression ( 'AND' exclusive_or_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_exclusive_or_expression_in_and_expression1531);
            exclusive_or_expression279=exclusive_or_expression();

            state._fsp--;

            adaptor.addChild(root_0, exclusive_or_expression279.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:237:43: ( 'AND' exclusive_or_expression )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==AND) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:237:44: 'AND' exclusive_or_expression
            	    {
            	    string_literal280=(Token)match(input,AND,FOLLOW_AND_in_and_expression1534); 
            	    string_literal280_tree = (Object)adaptor.create(string_literal280);
            	    root_0 = (Object)adaptor.becomeRoot(string_literal280_tree, root_0);

            	    pushFollow(FOLLOW_exclusive_or_expression_in_and_expression1537);
            	    exclusive_or_expression281=exclusive_or_expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, exclusive_or_expression281.getTree());

            	    }
            	    break;

            	default :
            	    break loop39;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "and_expression"

    public static class exclusive_or_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "exclusive_or_expression"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:238:1: exclusive_or_expression : equality_expression ( 'XOR' equality_expression )? ;
    public final SQLParser.exclusive_or_expression_return exclusive_or_expression() throws RecognitionException {
        SQLParser.exclusive_or_expression_return retval = new SQLParser.exclusive_or_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal283=null;
        SQLParser.equality_expression_return equality_expression282 = null;

        SQLParser.equality_expression_return equality_expression284 = null;


        Object string_literal283_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:238:25: ( equality_expression ( 'XOR' equality_expression )? )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:238:27: equality_expression ( 'XOR' equality_expression )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_equality_expression_in_exclusive_or_expression1546);
            equality_expression282=equality_expression();

            state._fsp--;

            adaptor.addChild(root_0, equality_expression282.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:238:47: ( 'XOR' equality_expression )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==XOR) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:238:48: 'XOR' equality_expression
                    {
                    string_literal283=(Token)match(input,XOR,FOLLOW_XOR_in_exclusive_or_expression1549); 
                    string_literal283_tree = (Object)adaptor.create(string_literal283);
                    root_0 = (Object)adaptor.becomeRoot(string_literal283_tree, root_0);

                    pushFollow(FOLLOW_equality_expression_in_exclusive_or_expression1552);
                    equality_expression284=equality_expression();

                    state._fsp--;

                    adaptor.addChild(root_0, equality_expression284.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "exclusive_or_expression"

    public static class equality_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "equality_expression"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:239:1: equality_expression : null_comparison ( ( '=' | '!=' | '<>' ) null_comparison )? ;
    public final SQLParser.equality_expression_return equality_expression() throws RecognitionException {
        SQLParser.equality_expression_return retval = new SQLParser.equality_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set286=null;
        SQLParser.null_comparison_return null_comparison285 = null;

        SQLParser.null_comparison_return null_comparison287 = null;


        Object set286_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:239:21: ( null_comparison ( ( '=' | '!=' | '<>' ) null_comparison )? )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:239:23: null_comparison ( ( '=' | '!=' | '<>' ) null_comparison )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_null_comparison_in_equality_expression1561);
            null_comparison285=null_comparison();

            state._fsp--;

            adaptor.addChild(root_0, null_comparison285.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:239:39: ( ( '=' | '!=' | '<>' ) null_comparison )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( ((LA41_0>=EQ && LA41_0<=LTGT)) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:239:40: ( '=' | '!=' | '<>' ) null_comparison
                    {
                    set286=(Token)input.LT(1);
                    set286=(Token)input.LT(1);
                    if ( (input.LA(1)>=EQ && input.LA(1)<=LTGT) ) {
                        input.consume();
                        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set286), root_0);
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_null_comparison_in_equality_expression1577);
                    null_comparison287=null_comparison();

                    state._fsp--;

                    adaptor.addChild(root_0, null_comparison287.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "equality_expression"

    public static class null_comparison_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "null_comparison"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:240:1: null_comparison : relational_expression ( 'IS' ( 'NOT' )? 'NULL' )? ;
    public final SQLParser.null_comparison_return null_comparison() throws RecognitionException {
        SQLParser.null_comparison_return retval = new SQLParser.null_comparison_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal289=null;
        Token string_literal290=null;
        Token string_literal291=null;
        SQLParser.relational_expression_return relational_expression288 = null;


        Object string_literal289_tree=null;
        Object string_literal290_tree=null;
        Object string_literal291_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:240:18: ( relational_expression ( 'IS' ( 'NOT' )? 'NULL' )? )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:240:20: relational_expression ( 'IS' ( 'NOT' )? 'NULL' )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_relational_expression_in_null_comparison1587);
            relational_expression288=relational_expression();

            state._fsp--;

            adaptor.addChild(root_0, relational_expression288.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:240:42: ( 'IS' ( 'NOT' )? 'NULL' )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==IS) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:240:43: 'IS' ( 'NOT' )? 'NULL'
                    {
                    string_literal289=(Token)match(input,IS,FOLLOW_IS_in_null_comparison1590); 
                    string_literal289_tree = (Object)adaptor.create(string_literal289);
                    root_0 = (Object)adaptor.becomeRoot(string_literal289_tree, root_0);

                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:240:49: ( 'NOT' )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==NOT) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:240:49: 'NOT'
                            {
                            string_literal290=(Token)match(input,NOT,FOLLOW_NOT_in_null_comparison1593); 
                            string_literal290_tree = (Object)adaptor.create(string_literal290);
                            adaptor.addChild(root_0, string_literal290_tree);


                            }
                            break;

                    }

                    string_literal291=(Token)match(input,NULL,FOLLOW_NULL_in_null_comparison1596); 

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "null_comparison"

    public static class relational_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relational_expression"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:241:1: relational_expression : in_expression ( ( '<=' | '>=' | '<' | '>' ) in_expression )? ;
    public final SQLParser.relational_expression_return relational_expression() throws RecognitionException {
        SQLParser.relational_expression_return retval = new SQLParser.relational_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set293=null;
        SQLParser.in_expression_return in_expression292 = null;

        SQLParser.in_expression_return in_expression294 = null;


        Object set293_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:241:23: ( in_expression ( ( '<=' | '>=' | '<' | '>' ) in_expression )? )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:241:25: in_expression ( ( '<=' | '>=' | '<' | '>' ) in_expression )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_in_expression_in_relational_expression1606);
            in_expression292=in_expression();

            state._fsp--;

            adaptor.addChild(root_0, in_expression292.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:241:39: ( ( '<=' | '>=' | '<' | '>' ) in_expression )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( ((LA44_0>=GT && LA44_0<=LE)) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:241:40: ( '<=' | '>=' | '<' | '>' ) in_expression
                    {
                    set293=(Token)input.LT(1);
                    set293=(Token)input.LT(1);
                    if ( (input.LA(1)>=GT && input.LA(1)<=LE) ) {
                        input.consume();
                        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set293), root_0);
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_in_expression_in_relational_expression1626);
                    in_expression294=in_expression();

                    state._fsp--;

                    adaptor.addChild(root_0, in_expression294.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "relational_expression"

    public static class in_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "in_expression"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:242:1: in_expression : between_expression ( ( 'NOT' )? 'IN' '(' value_list ')' )? ;
    public final SQLParser.in_expression_return in_expression() throws RecognitionException {
        SQLParser.in_expression_return retval = new SQLParser.in_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal296=null;
        Token string_literal297=null;
        Token char_literal298=null;
        Token char_literal300=null;
        SQLParser.between_expression_return between_expression295 = null;

        SQLParser.value_list_return value_list299 = null;


        Object string_literal296_tree=null;
        Object string_literal297_tree=null;
        Object char_literal298_tree=null;
        Object char_literal300_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:242:16: ( between_expression ( ( 'NOT' )? 'IN' '(' value_list ')' )? )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:242:18: between_expression ( ( 'NOT' )? 'IN' '(' value_list ')' )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_between_expression_in_in_expression1636);
            between_expression295=between_expression();

            state._fsp--;

            adaptor.addChild(root_0, between_expression295.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:242:37: ( ( 'NOT' )? 'IN' '(' value_list ')' )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==NOT||LA46_0==IN) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:242:38: ( 'NOT' )? 'IN' '(' value_list ')'
                    {
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:242:38: ( 'NOT' )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==NOT) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:242:38: 'NOT'
                            {
                            string_literal296=(Token)match(input,NOT,FOLLOW_NOT_in_in_expression1639); 
                            string_literal296_tree = (Object)adaptor.create(string_literal296);
                            adaptor.addChild(root_0, string_literal296_tree);


                            }
                            break;

                    }

                    string_literal297=(Token)match(input,IN,FOLLOW_IN_in_in_expression1642); 
                    string_literal297_tree = (Object)adaptor.create(string_literal297);
                    root_0 = (Object)adaptor.becomeRoot(string_literal297_tree, root_0);

                    char_literal298=(Token)match(input,66,FOLLOW_66_in_in_expression1645); 
                    pushFollow(FOLLOW_value_list_in_in_expression1648);
                    value_list299=value_list();

                    state._fsp--;

                    adaptor.addChild(root_0, value_list299.getTree());
                    char_literal300=(Token)match(input,67,FOLLOW_67_in_in_expression1650); 

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "in_expression"

    public static class between_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "between_expression"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:243:1: between_expression : additive_expression ( 'BETWEEN' additive_expression 'AND' additive_expression )? ;
    public final SQLParser.between_expression_return between_expression() throws RecognitionException {
        SQLParser.between_expression_return retval = new SQLParser.between_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal302=null;
        Token string_literal304=null;
        SQLParser.additive_expression_return additive_expression301 = null;

        SQLParser.additive_expression_return additive_expression303 = null;

        SQLParser.additive_expression_return additive_expression305 = null;


        Object string_literal302_tree=null;
        Object string_literal304_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:243:20: ( additive_expression ( 'BETWEEN' additive_expression 'AND' additive_expression )? )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:243:22: additive_expression ( 'BETWEEN' additive_expression 'AND' additive_expression )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_additive_expression_in_between_expression1660);
            additive_expression301=additive_expression();

            state._fsp--;

            adaptor.addChild(root_0, additive_expression301.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:243:42: ( 'BETWEEN' additive_expression 'AND' additive_expression )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==BETWEEN) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:243:43: 'BETWEEN' additive_expression 'AND' additive_expression
                    {
                    string_literal302=(Token)match(input,BETWEEN,FOLLOW_BETWEEN_in_between_expression1663); 
                    string_literal302_tree = (Object)adaptor.create(string_literal302);
                    root_0 = (Object)adaptor.becomeRoot(string_literal302_tree, root_0);

                    pushFollow(FOLLOW_additive_expression_in_between_expression1666);
                    additive_expression303=additive_expression();

                    state._fsp--;

                    adaptor.addChild(root_0, additive_expression303.getTree());
                    string_literal304=(Token)match(input,AND,FOLLOW_AND_in_between_expression1668); 
                    pushFollow(FOLLOW_additive_expression_in_between_expression1671);
                    additive_expression305=additive_expression();

                    state._fsp--;

                    adaptor.addChild(root_0, additive_expression305.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "between_expression"

    public static class additive_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "additive_expression"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:244:1: additive_expression : multiplicative_expression ( ( '+' | '-' | '||' ) multiplicative_expression )* ;
    public final SQLParser.additive_expression_return additive_expression() throws RecognitionException {
        SQLParser.additive_expression_return retval = new SQLParser.additive_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set307=null;
        SQLParser.multiplicative_expression_return multiplicative_expression306 = null;

        SQLParser.multiplicative_expression_return multiplicative_expression308 = null;


        Object set307_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:244:21: ( multiplicative_expression ( ( '+' | '-' | '||' ) multiplicative_expression )* )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:244:23: multiplicative_expression ( ( '+' | '-' | '||' ) multiplicative_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_multiplicative_expression_in_additive_expression1680);
            multiplicative_expression306=multiplicative_expression();

            state._fsp--;

            adaptor.addChild(root_0, multiplicative_expression306.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:244:49: ( ( '+' | '-' | '||' ) multiplicative_expression )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( ((LA48_0>=PLUS && LA48_0<=SUB)||LA48_0==BARBAR) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:244:50: ( '+' | '-' | '||' ) multiplicative_expression
            	    {
            	    set307=(Token)input.LT(1);
            	    set307=(Token)input.LT(1);
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=SUB)||input.LA(1)==BARBAR ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set307), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplicative_expression_in_additive_expression1696);
            	    multiplicative_expression308=multiplicative_expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, multiplicative_expression308.getTree());

            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "additive_expression"

    public static class multiplicative_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiplicative_expression"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:245:1: multiplicative_expression : unary_expression ( ( '*' | '/' | '%' ) unary_expression )* ;
    public final SQLParser.multiplicative_expression_return multiplicative_expression() throws RecognitionException {
        SQLParser.multiplicative_expression_return retval = new SQLParser.multiplicative_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set310=null;
        SQLParser.unary_expression_return unary_expression309 = null;

        SQLParser.unary_expression_return unary_expression311 = null;


        Object set310_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:245:26: ( unary_expression ( ( '*' | '/' | '%' ) unary_expression )* )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:245:28: unary_expression ( ( '*' | '/' | '%' ) unary_expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_unary_expression_in_multiplicative_expression1704);
            unary_expression309=unary_expression();

            state._fsp--;

            adaptor.addChild(root_0, unary_expression309.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:245:45: ( ( '*' | '/' | '%' ) unary_expression )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( ((LA49_0>=STAR && LA49_0<=PERCENT)) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:245:46: ( '*' | '/' | '%' ) unary_expression
            	    {
            	    set310=(Token)input.LT(1);
            	    set310=(Token)input.LT(1);
            	    if ( (input.LA(1)>=STAR && input.LA(1)<=PERCENT) ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set310), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unary_expression_in_multiplicative_expression1720);
            	    unary_expression311=unary_expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, unary_expression311.getTree());

            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "multiplicative_expression"

    public static class unary_expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_expression"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:246:1: unary_expression : ( '-' primary | 'NOT' primary | primary );
    public final SQLParser.unary_expression_return unary_expression() throws RecognitionException {
        SQLParser.unary_expression_return retval = new SQLParser.unary_expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal312=null;
        Token string_literal314=null;
        SQLParser.primary_return primary313 = null;

        SQLParser.primary_return primary315 = null;

        SQLParser.primary_return primary316 = null;


        Object char_literal312_tree=null;
        Object string_literal314_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:247:5: ( '-' primary | 'NOT' primary | primary )
            int alt50=3;
            switch ( input.LA(1) ) {
            case SUB:
                {
                alt50=1;
                }
                break;
            case NOT:
                {
                alt50=2;
                }
                break;
            case INT:
            case IDENTIFIER:
            case STRING:
            case QUOTED_NAME:
            case PRIMARY:
            case DEFAULT:
            case COMMENT:
            case IS:
            case NULL:
            case 62:
            case 65:
            case 66:
            case 74:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 86:
            case 90:
            case 98:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 121:
            case 122:
            case 123:
            case 129:
            case 133:
            case 135:
            case 137:
                {
                alt50=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }

            switch (alt50) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:247:9: '-' primary
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal312=(Token)match(input,SUB,FOLLOW_SUB_in_unary_expression1736); 
                    char_literal312_tree = (Object)adaptor.create(char_literal312);
                    root_0 = (Object)adaptor.becomeRoot(char_literal312_tree, root_0);

                    pushFollow(FOLLOW_primary_in_unary_expression1739);
                    primary313=primary();

                    state._fsp--;

                    adaptor.addChild(root_0, primary313.getTree());

                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:248:9: 'NOT' primary
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal314=(Token)match(input,NOT,FOLLOW_NOT_in_unary_expression1749); 
                    string_literal314_tree = (Object)adaptor.create(string_literal314);
                    root_0 = (Object)adaptor.becomeRoot(string_literal314_tree, root_0);

                    pushFollow(FOLLOW_primary_in_unary_expression1752);
                    primary315=primary();

                    state._fsp--;

                    adaptor.addChild(root_0, primary315.getTree());

                    }
                    break;
                case 3 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:249:9: primary
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_primary_in_unary_expression1762);
                    primary316=primary();

                    state._fsp--;

                    adaptor.addChild(root_0, primary316.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "unary_expression"

    public static class primary_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primary"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:252:1: primary : ( '(' expression ')' | IDENTIFIER arguments -> ^( INVOCATION IDENTIFIER arguments ) | value );
    public final SQLParser.primary_return primary() throws RecognitionException {
        SQLParser.primary_return retval = new SQLParser.primary_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal317=null;
        Token char_literal319=null;
        Token IDENTIFIER320=null;
        SQLParser.expression_return expression318 = null;

        SQLParser.arguments_return arguments321 = null;

        SQLParser.value_return value322 = null;


        Object char_literal317_tree=null;
        Object char_literal319_tree=null;
        Object IDENTIFIER320_tree=null;
        RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
        RewriteRuleSubtreeStream stream_arguments=new RewriteRuleSubtreeStream(adaptor,"rule arguments");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:253:5: ( '(' expression ')' | IDENTIFIER arguments -> ^( INVOCATION IDENTIFIER arguments ) | value )
            int alt51=3;
            switch ( input.LA(1) ) {
            case 66:
                {
                alt51=1;
                }
                break;
            case IDENTIFIER:
                {
                int LA51_2 = input.LA(2);

                if ( (LA51_2==66) ) {
                    alt51=2;
                }
                else if ( (LA51_2==EOF||LA51_2==NOT||(LA51_2>=OR && LA51_2<=IS)||(LA51_2>=IN && LA51_2<=BARBAR)||(LA51_2>=67 && LA51_2<=68)||(LA51_2>=75 && LA51_2<=82)||(LA51_2>=85 && LA51_2<=86)||LA51_2==90||LA51_2==97||(LA51_2>=108 && LA51_2<=117)) ) {
                    alt51=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 51, 2, input);

                    throw nvae;
                }
                }
                break;
            case INT:
            case STRING:
            case QUOTED_NAME:
            case PRIMARY:
            case DEFAULT:
            case COMMENT:
            case IS:
            case NULL:
            case 62:
            case 65:
            case 74:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 86:
            case 90:
            case 98:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 121:
            case 122:
            case 123:
            case 129:
            case 133:
            case 135:
            case 137:
                {
                alt51=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }

            switch (alt51) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:253:9: '(' expression ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal317=(Token)match(input,66,FOLLOW_66_in_primary1782); 
                    pushFollow(FOLLOW_expression_in_primary1785);
                    expression318=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression318.getTree());
                    char_literal319=(Token)match(input,67,FOLLOW_67_in_primary1787); 

                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:254:9: IDENTIFIER arguments
                    {
                    IDENTIFIER320=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_primary1798);  
                    stream_IDENTIFIER.add(IDENTIFIER320);

                    pushFollow(FOLLOW_arguments_in_primary1800);
                    arguments321=arguments();

                    state._fsp--;

                    stream_arguments.add(arguments321.getTree());


                    // AST REWRITE
                    // elements: IDENTIFIER, arguments
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 254:30: -> ^( INVOCATION IDENTIFIER arguments )
                    {
                        // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:254:33: ^( INVOCATION IDENTIFIER arguments )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(INVOCATION, "INVOCATION"), root_1);

                        adaptor.addChild(root_1, stream_IDENTIFIER.nextNode());
                        adaptor.addChild(root_1, stream_arguments.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:255:9: value
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_value_in_primary1820);
                    value322=value();

                    state._fsp--;

                    adaptor.addChild(root_0, value322.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "primary"

    public static class arguments_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arguments"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:258:1: arguments : '(' ( expression ( ',' expression )* )? ')' -> ^( ARGUMENTS ( expression )* ) ;
    public final SQLParser.arguments_return arguments() throws RecognitionException {
        SQLParser.arguments_return retval = new SQLParser.arguments_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal323=null;
        Token char_literal325=null;
        Token char_literal327=null;
        SQLParser.expression_return expression324 = null;

        SQLParser.expression_return expression326 = null;


        Object char_literal323_tree=null;
        Object char_literal325_tree=null;
        Object char_literal327_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:258:11: ( '(' ( expression ( ',' expression )* )? ')' -> ^( ARGUMENTS ( expression )* ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:258:13: '(' ( expression ( ',' expression )* )? ')'
            {
            char_literal323=(Token)match(input,66,FOLLOW_66_in_arguments1833);  
            stream_66.add(char_literal323);

            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:258:17: ( expression ( ',' expression )* )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( ((LA53_0>=INT && LA53_0<=COMMENT)||(LA53_0>=IS && LA53_0<=NULL)||LA53_0==SUB||LA53_0==62||(LA53_0>=65 && LA53_0<=66)||LA53_0==74||(LA53_0>=76 && LA53_0<=80)||LA53_0==86||LA53_0==90||LA53_0==98||(LA53_0>=109 && LA53_0<=116)||(LA53_0>=121 && LA53_0<=123)||LA53_0==129||LA53_0==133||LA53_0==135||LA53_0==137) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:258:18: expression ( ',' expression )*
                    {
                    pushFollow(FOLLOW_expression_in_arguments1836);
                    expression324=expression();

                    state._fsp--;

                    stream_expression.add(expression324.getTree());
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:258:29: ( ',' expression )*
                    loop52:
                    do {
                        int alt52=2;
                        int LA52_0 = input.LA(1);

                        if ( (LA52_0==68) ) {
                            alt52=1;
                        }


                        switch (alt52) {
                    	case 1 :
                    	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:258:30: ',' expression
                    	    {
                    	    char_literal325=(Token)match(input,68,FOLLOW_68_in_arguments1839);  
                    	    stream_68.add(char_literal325);

                    	    pushFollow(FOLLOW_expression_in_arguments1841);
                    	    expression326=expression();

                    	    state._fsp--;

                    	    stream_expression.add(expression326.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop52;
                        }
                    } while (true);


                    }
                    break;

            }

            char_literal327=(Token)match(input,67,FOLLOW_67_in_arguments1847);  
            stream_67.add(char_literal327);



            // AST REWRITE
            // elements: expression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 258:53: -> ^( ARGUMENTS ( expression )* )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:258:56: ^( ARGUMENTS ( expression )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ARGUMENTS, "ARGUMENTS"), root_1);

                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:258:68: ( expression )*
                while ( stream_expression.hasNext() ) {
                    adaptor.addChild(root_1, stream_expression.nextTree());

                }
                stream_expression.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "arguments"

    public static class expression_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression_list"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:259:1: expression_list : expression ( ',' expression )* ;
    public final SQLParser.expression_list_return expression_list() throws RecognitionException {
        SQLParser.expression_list_return retval = new SQLParser.expression_list_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal329=null;
        SQLParser.expression_return expression328 = null;

        SQLParser.expression_return expression330 = null;


        Object char_literal329_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:259:17: ( expression ( ',' expression )* )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:259:19: expression ( ',' expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_expression_list1863);
            expression328=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression328.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:259:30: ( ',' expression )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==68) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:259:31: ',' expression
            	    {
            	    char_literal329=(Token)match(input,68,FOLLOW_68_in_expression_list1866); 
            	    pushFollow(FOLLOW_expression_in_expression_list1869);
            	    expression330=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, expression330.getTree());

            	    }
            	    break;

            	default :
            	    break loop54;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "expression_list"

    public static class qualified_name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "qualified_name"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:260:1: qualified_name : name ( '.' name )* ;
    public final SQLParser.qualified_name_return qualified_name() throws RecognitionException {
        SQLParser.qualified_name_return retval = new SQLParser.qualified_name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal332=null;
        SQLParser.name_return name331 = null;

        SQLParser.name_return name333 = null;


        Object char_literal332_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:260:16: ( name ( '.' name )* )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:260:18: name ( '.' name )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_name_in_qualified_name1878);
            name331=name();

            state._fsp--;

            adaptor.addChild(root_0, name331.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:260:23: ( '.' name )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==136) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:260:24: '.' name
            	    {
            	    char_literal332=(Token)match(input,136,FOLLOW_136_in_qualified_name1881); 
            	    pushFollow(FOLLOW_name_in_qualified_name1884);
            	    name333=name();

            	    state._fsp--;

            	    adaptor.addChild(root_0, name333.getTree());

            	    }
            	    break;

            	default :
            	    break loop55;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "qualified_name"

    public static class value_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "value_list"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:261:1: value_list : value ( ',' value )* -> ^( VALUE_LIST ( value )* ) ;
    public final SQLParser.value_list_return value_list() throws RecognitionException {
        SQLParser.value_list_return retval = new SQLParser.value_list_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal335=null;
        SQLParser.value_return value334 = null;

        SQLParser.value_return value336 = null;


        Object char_literal335_tree=null;
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:261:12: ( value ( ',' value )* -> ^( VALUE_LIST ( value )* ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:261:14: value ( ',' value )*
            {
            pushFollow(FOLLOW_value_in_value_list1893);
            value334=value();

            state._fsp--;

            stream_value.add(value334.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:261:20: ( ',' value )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==68) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:261:21: ',' value
            	    {
            	    char_literal335=(Token)match(input,68,FOLLOW_68_in_value_list1896);  
            	    stream_68.add(char_literal335);

            	    pushFollow(FOLLOW_value_in_value_list1898);
            	    value336=value();

            	    state._fsp--;

            	    stream_value.add(value336.getTree());

            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);



            // AST REWRITE
            // elements: value
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 261:33: -> ^( VALUE_LIST ( value )* )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:261:36: ^( VALUE_LIST ( value )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VALUE_LIST, "VALUE_LIST"), root_1);

                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:261:49: ( value )*
                while ( stream_value.hasNext() ) {
                    adaptor.addChild(root_1, stream_value.nextTree());

                }
                stream_value.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "value_list"

    public static class value_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "value"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:262:1: value : ( name | literal );
    public final SQLParser.value_return value() throws RecognitionException {
        SQLParser.value_return retval = new SQLParser.value_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        SQLParser.name_return name337 = null;

        SQLParser.literal_return literal338 = null;



        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:262:8: ( name | literal )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==IDENTIFIER||(LA57_0>=QUOTED_NAME && LA57_0<=COMMENT)||LA57_0==IS||LA57_0==62||LA57_0==65||LA57_0==74||(LA57_0>=76 && LA57_0<=80)||LA57_0==86||LA57_0==90||LA57_0==98||(LA57_0>=109 && LA57_0<=116)||(LA57_0>=121 && LA57_0<=123)||LA57_0==129||LA57_0==133||LA57_0==135||LA57_0==137) ) {
                alt57=1;
            }
            else if ( (LA57_0==INT||LA57_0==STRING||LA57_0==NULL) ) {
                alt57=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }
            switch (alt57) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:262:10: name
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_name_in_value1917);
                    name337=name();

                    state._fsp--;

                    adaptor.addChild(root_0, name337.getTree());

                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:262:17: literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_literal_in_value1921);
                    literal338=literal();

                    state._fsp--;

                    adaptor.addChild(root_0, literal338.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "value"

    public static class name_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "name_list"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:263:1: name_list : name ( ',' name )* -> ^( NAME_LIST ( name )* ) ;
    public final SQLParser.name_list_return name_list() throws RecognitionException {
        SQLParser.name_list_return retval = new SQLParser.name_list_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal340=null;
        SQLParser.name_return name339 = null;

        SQLParser.name_return name341 = null;


        Object char_literal340_tree=null;
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:263:11: ( name ( ',' name )* -> ^( NAME_LIST ( name )* ) )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:263:13: name ( ',' name )*
            {
            pushFollow(FOLLOW_name_in_name_list1928);
            name339=name();

            state._fsp--;

            stream_name.add(name339.getTree());
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:263:18: ( ',' name )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==68) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:263:19: ',' name
            	    {
            	    char_literal340=(Token)match(input,68,FOLLOW_68_in_name_list1931);  
            	    stream_68.add(char_literal340);

            	    pushFollow(FOLLOW_name_in_name_list1933);
            	    name341=name();

            	    state._fsp--;

            	    stream_name.add(name341.getTree());

            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);



            // AST REWRITE
            // elements: name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 263:30: -> ^( NAME_LIST ( name )* )
            {
                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:263:33: ^( NAME_LIST ( name )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME_LIST, "NAME_LIST"), root_1);

                // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:263:45: ( name )*
                while ( stream_name.hasNext() ) {
                    adaptor.addChild(root_1, stream_name.nextTree());

                }
                stream_name.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "name_list"

    public static class name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "name"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:264:1: name : ( IDENTIFIER | ora_key | keyword | QUOTED_NAME );
    public final SQLParser.name_return name() throws RecognitionException {
        SQLParser.name_return retval = new SQLParser.name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IDENTIFIER342=null;
        Token QUOTED_NAME345=null;
        SQLParser.ora_key_return ora_key343 = null;

        SQLParser.keyword_return keyword344 = null;


        Object IDENTIFIER342_tree=null;
        Object QUOTED_NAME345_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:264:7: ( IDENTIFIER | ora_key | keyword | QUOTED_NAME )
            int alt59=4;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                alt59=1;
                }
                break;
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 90:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
                {
                alt59=2;
                }
                break;
            case PRIMARY:
            case DEFAULT:
            case COMMENT:
            case IS:
            case 62:
            case 65:
            case 74:
            case 86:
            case 98:
            case 121:
            case 122:
            case 123:
            case 129:
            case 133:
            case 135:
            case 137:
                {
                alt59=3;
                }
                break;
            case QUOTED_NAME:
                {
                alt59=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }

            switch (alt59) {
                case 1 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:264:9: IDENTIFIER
                    {
                    root_0 = (Object)adaptor.nil();

                    IDENTIFIER342=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_name1952); 
                    IDENTIFIER342_tree = (Object)adaptor.create(IDENTIFIER342);
                    adaptor.addChild(root_0, IDENTIFIER342_tree);


                    }
                    break;
                case 2 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:264:22: ora_key
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ora_key_in_name1956);
                    ora_key343=ora_key();

                    state._fsp--;

                    adaptor.addChild(root_0, ora_key343.getTree());

                    }
                    break;
                case 3 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:264:32: keyword
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_keyword_in_name1960);
                    keyword344=keyword();

                    state._fsp--;

                    adaptor.addChild(root_0, keyword344.getTree());

                    }
                    break;
                case 4 :
                    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:264:42: QUOTED_NAME
                    {
                    root_0 = (Object)adaptor.nil();

                    QUOTED_NAME345=(Token)match(input,QUOTED_NAME,FOLLOW_QUOTED_NAME_in_name1964); 
                    QUOTED_NAME345_tree = (Object)adaptor.create(QUOTED_NAME345);
                    adaptor.addChild(root_0, QUOTED_NAME345_tree);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "name"

    public static class literal_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "literal"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:266:1: literal : ( INT | STRING | 'NULL' );
    public final SQLParser.literal_return literal() throws RecognitionException {
        SQLParser.literal_return retval = new SQLParser.literal_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set346=null;

        Object set346_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:266:10: ( INT | STRING | 'NULL' )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:
            {
            root_0 = (Object)adaptor.nil();

            set346=(Token)input.LT(1);
            if ( input.LA(1)==INT||input.LA(1)==STRING||input.LA(1)==NULL ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set346));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "literal"

    public static class keyword_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "keyword"
    // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:268:1: keyword : ( 'CREATE' | 'DROP' | 'ALTER' | 'TABLE' | 'INDEX' | 'SEQUENCE' | 'TRIGGER' | 'UNIQUE' | 'BY' | 'ON' | 'IS' | 'COLUMN' | PRIMARY | 'KEY' | DEFAULT | COMMENT );
    public final SQLParser.keyword_return keyword() throws RecognitionException {
        SQLParser.keyword_return retval = new SQLParser.keyword_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set347=null;

        Object set347_tree=null;

        try {
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:268:9: ( 'CREATE' | 'DROP' | 'ALTER' | 'TABLE' | 'INDEX' | 'SEQUENCE' | 'TRIGGER' | 'UNIQUE' | 'BY' | 'ON' | 'IS' | 'COLUMN' | PRIMARY | 'KEY' | DEFAULT | COMMENT )
            // /Users/volker/Documents/databene/jdbacl/src/main/g/SQL.g:
            {
            root_0 = (Object)adaptor.nil();

            set347=(Token)input.LT(1);
            if ( (input.LA(1)>=PRIMARY && input.LA(1)<=COMMENT)||input.LA(1)==IS||input.LA(1)==62||input.LA(1)==65||input.LA(1)==74||input.LA(1)==86||input.LA(1)==98||(input.LA(1)>=121 && input.LA(1)<=123)||input.LA(1)==129||input.LA(1)==133||input.LA(1)==135||input.LA(1)==137 ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set347));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }

        	catch (RecognitionException e) {
        	  throw e;
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "keyword"

    // Delegated rules


    protected DFA2 dfa2 = new DFA2(this);
    static final String DFA2_eotS =
        "\13\uffff";
    static final String DFA2_eofS =
        "\13\uffff";
    static final String DFA2_minS =
        "\1\76\1\77\1\uffff\1\101\7\uffff";
    static final String DFA2_maxS =
        "\1\u0086\1\u0085\1\uffff\1\u0085\7\uffff";
    static final String DFA2_acceptS =
        "\2\uffff\1\2\1\uffff\1\4\1\7\1\1\1\5\1\3\1\6\1\10";
    static final String DFA2_specialS =
        "\13\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\1\73\uffff\1\4\1\3\12\uffff\1\2",
            "\3\6\67\uffff\1\7\7\uffff\1\7\3\uffff\1\5",
            "",
            "\1\10\67\uffff\1\11\13\uffff\1\12",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "95:1: command : ( ( create_table ';' ) | ( comment ';' ) | ( drop_table ';' ) | ( alter_table ';' ) | ( create_index ';' ) | ( drop_index ';' ) | ( create_sequence ';' ) | ( drop_sequence ';' ) );";
        }
    }
 

    public static final BitSet FOLLOW_command_in_commands58 = new BitSet(new long[]{0x4000000000000002L,0x0C00000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_create_table_in_command68 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_command73 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_comment_in_command81 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_command91 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_drop_table_in_command99 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_command106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alter_table_in_command114 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_command120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_index_in_command128 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_command133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_drop_index_in_command141 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_command148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_create_sequence_in_command156 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_command158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_drop_sequence_in_command166 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_command170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_create_table184 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_63_in_create_table186 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_64_in_create_table189 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_create_table192 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_table_name_in_create_table194 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_create_table196 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F602L,0x00000000000002A2L});
    public static final BitSet FOLLOW_table_details_in_create_table198 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_create_table200 = new BitSet(new long[]{0x0000000000000000L,0x003FE0020467F800L});
    public static final BitSet FOLLOW_ora_configs_in_create_table202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualified_name_in_table_name221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_table_detail_in_table_details236 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_table_details239 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F602L,0x00000000000002A2L});
    public static final BitSet FOLLOW_table_detail_in_table_details241 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_column_spec_in_table_detail259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inline_constraint_in_table_detail263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_column_spec270 = new BitSet(new long[]{0x0778000000000000L,0x00000000000001E0L});
    public static final BitSet FOLLOW_type_in_column_spec272 = new BitSet(new long[]{0x0000040000000422L,0x0000000000000004L});
    public static final BitSet FOLLOW_size_in_column_spec274 = new BitSet(new long[]{0x0000040000000422L});
    public static final BitSet FOLLOW_column_detail_in_column_spec277 = new BitSet(new long[]{0x0000040000000422L});
    public static final BitSet FOLLOW_set_in_type0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_size370 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_size372 = new BitSet(new long[]{0x0180000000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_size_uom_in_size374 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_fractionDigits_in_size377 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_size380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_fractionDigits401 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_fractionDigits404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_size_uom0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nullability_in_column_detail422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_default_value_in_column_detail426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_nullability433 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_NULL_in_nullability437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DEFAULT_in_default_value444 = new BitSet(new long[]{0x4000060000000FD0L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_value_in_default_value447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_inline_constraint455 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_inline_constraint458 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_PRIMARY_in_inline_constraint462 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_inline_constraint465 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_inline_constraint468 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_list_in_inline_constraint471 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_inline_constraint473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ora_config_in_ora_configs483 = new BitSet(new long[]{0x0000000000000002L,0x003FE0020467F800L});
    public static final BitSet FOLLOW_75_in_ora_config502 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_ora_config505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_ora_config512 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_ora_config515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_ora_config522 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_ora_config525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_ora_config532 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_ora_config535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_ora_config542 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_ora_config545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_ora_config552 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_ora_config555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_ora_config562 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_ora_config565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ora_storage_spec_in_ora_config571 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ora_partition_spec_in_ora_config576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ora_flag_in_ora_config581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ora_lob_in_ora_config586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_ora_config592 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ora_config594 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_ora_config597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_ora_config605 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ora_config607 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_ora_config610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_ora_config618 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_ora_config620 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_ora_config622 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_ora_config624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_ora_key636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_ora_key640 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_ora_key644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_ora_key648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_ora_key652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_ora_key656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ora_flag_in_ora_key660 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_ora_storage_spec667 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_ora_storage_spec670 = new BitSet(new long[]{0x0000000000000000L,0x00000001F8000008L});
    public static final BitSet FOLLOW_ora_storage_opt_in_ora_storage_spec672 = new BitSet(new long[]{0x0000000000000000L,0x00000001F8000008L});
    public static final BitSet FOLLOW_67_in_ora_storage_spec675 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_ora_storage_opt684 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_ora_storage_opt691 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENTIFIER_in_ora_storage_opt693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_ora_storage_opt700 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_ora_storage_opt710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_ora_storage_opt717 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_ora_storage_opt721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_ora_storage_opt728 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_IDENTIFIER_in_ora_storage_opt732 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_ora_storage_opt739 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_ora_storage_opt742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_ora_storage_opt749 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_ora_storage_opt752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_ora_partition_spec764 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_ora_partition_spec767 = new BitSet(new long[]{0x0000000000000000L,0x0000003800000000L});
    public static final BitSet FOLLOW_set_in_ora_partition_spec770 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_ora_partition_spec782 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_ora_partition_spec785 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_ora_partition_spec787 = new BitSet(new long[]{0x0000000000000000L,0x000000C000000004L});
    public static final BitSet FOLLOW_102_in_ora_partition_spec791 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_ora_partition_spec793 = new BitSet(new long[]{0x0000000000000000L,0x0000001800000000L});
    public static final BitSet FOLLOW_set_in_ora_partition_spec796 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_ora_partition_spec804 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_ora_partition_spec807 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_ora_partition_spec809 = new BitSet(new long[]{0x0000000000000000L,0x000000C000000004L});
    public static final BitSet FOLLOW_partition_part_config_in_ora_partition_spec814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_partition_part_config821 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_partition_parts_in_partition_part_config824 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_partition_part_config826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_partition_part_config834 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_partition_part_config836 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_104_in_partition_part_config838 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_IN_in_partition_part_config840 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_partition_part_config842 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_list_in_partition_part_config844 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_partition_part_config846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_partition_part_in_partition_parts853 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_partition_parts856 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_partition_part_in_partition_parts858 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_97_in_partition_part867 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_partition_part870 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_105_in_partition_part872 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000004L});
    public static final BitSet FOLLOW_partition_value_spec_in_partition_part874 = new BitSet(new long[]{0x0000000000000000L,0x003FE0020467F800L});
    public static final BitSet FOLLOW_ora_configs_in_partition_part876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_partition_value_spec884 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_107_in_partition_value_spec886 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_expression_in_partition_value_spec888 = new BitSet(new long[]{0x0000000000000000L,0x003FF0020467F800L});
    public static final BitSet FOLLOW_ora_configs_in_partition_value_spec890 = new BitSet(new long[]{0x0000000000000000L,0x003FF0020467F800L});
    public static final BitSet FOLLOW_sub_partition_config_in_partition_value_spec892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_partition_value_spec901 = new BitSet(new long[]{0x4000060000000FD0L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_value_list_in_partition_value_spec903 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_partition_value_spec905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_sub_partition_config913 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_sub_partition_config916 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_104_in_sub_partition_config918 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_IN_in_sub_partition_config920 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_sub_partition_config922 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_list_in_sub_partition_config924 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_sub_partition_config926 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_ora_flag0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_117_in_ora_lob981 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_ora_lob983 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_column_name_in_ora_lob985 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_ora_lob987 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_104_in_ora_lob989 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_118_in_ora_lob991 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_ora_lob993 = new BitSet(new long[]{0x0000000000000000L,0x039FE0000405F008L});
    public static final BitSet FOLLOW_ora_lob_config_in_ora_lob995 = new BitSet(new long[]{0x0000000000000000L,0x039FE0000405F008L});
    public static final BitSet FOLLOW_67_in_ora_lob998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_ora_lob_config1007 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_ora_lob_config1010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_ora_lob_config1017 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_ora_lob_config1019 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_IN_in_ora_lob_config1021 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_ora_lob_config1023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_119_in_ora_lob_config1030 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_ora_lob_config1033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_120_in_ora_lob_config1040 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_ora_lob_config1043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ora_flag_in_ora_lob_config1049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ora_lob_index_in_ora_lob_config1054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ora_storage_spec_in_ora_lob_config1059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_121_in_ora_lob_index1070 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_ora_lob_index1072 = new BitSet(new long[]{0x0000000000000000L,0x003FE0020467F808L});
    public static final BitSet FOLLOW_ora_configs_in_ora_lob_index1074 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_ora_lob_index1076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_alter_table1085 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_alter_table1087 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_table_name_in_alter_table1089 = new BitSet(new long[]{0x0000000000000000L,0x2800000000000000L});
    public static final BitSet FOLLOW_table_mutation_in_alter_table1091 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_drop_pk_in_table_mutation1109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_add_constraints_in_table_mutation1114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_123_in_drop_pk1124 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_PRIMARY_in_drop_pk1126 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_drop_pk1128 = new BitSet(new long[]{0x0000000000000002L,0x1000000000000000L});
    public static final BitSet FOLLOW_124_in_drop_pk1130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_125_in_add_constraints1138 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_add_constraints1140 = new BitSet(new long[]{0x0000000000000200L,0x8000000000000200L,0x0000000000000006L});
    public static final BitSet FOLLOW_constraint_spec_in_add_constraints1142 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_68_in_add_constraints1145 = new BitSet(new long[]{0x0000000000000200L,0x8000000000000200L,0x0000000000000006L});
    public static final BitSet FOLLOW_constraint_spec_in_add_constraints1147 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_67_in_add_constraints1151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_constraint_spec1159 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_constraint_spec1161 = new BitSet(new long[]{0x0000000000000200L,0x8000000000000200L,0x0000000000000006L});
    public static final BitSet FOLLOW_constraint_detail_in_constraint_spec1165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primary_key_constraint_in_constraint_detail1173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_foreign_key_constraint_in_constraint_detail1178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unique_constraint_in_constraint_detail1183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_check_constraint_in_constraint_detail1188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRIMARY_in_primary_key_constraint1195 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_primary_key_constraint1197 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_primary_key_constraint1199 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_list_in_primary_key_constraint1201 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_primary_key_constraint1203 = new BitSet(new long[]{0x0000000000000002L,0x4000000000000000L});
    public static final BitSet FOLLOW_126_in_primary_key_constraint1206 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_121_in_primary_key_constraint1208 = new BitSet(new long[]{0x0000000000000000L,0x003FE0020467F800L});
    public static final BitSet FOLLOW_ora_configs_in_primary_key_constraint1210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_foreign_key_constraint1219 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_foreign_key_constraint1221 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_foreign_key_constraint1223 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_list_in_foreign_key_constraint1225 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_foreign_key_constraint1227 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_128_in_foreign_key_constraint1229 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_table_name_in_foreign_key_constraint1231 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_foreign_key_constraint1233 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_list_in_foreign_key_constraint1237 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F40AL,0x00000000000002A2L});
    public static final BitSet FOLLOW_67_in_foreign_key_constraint1241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_unique_constraint1248 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_unique_constraint1250 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_list_in_unique_constraint1252 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_unique_constraint1254 = new BitSet(new long[]{0x0000000000000002L,0x4000000000000000L});
    public static final BitSet FOLLOW_126_in_unique_constraint1257 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_121_in_unique_constraint1259 = new BitSet(new long[]{0x0000000000000000L,0x003FE0020467F800L});
    public static final BitSet FOLLOW_ora_configs_in_unique_constraint1261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_check_constraint1270 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_expression_in_check_constraint1272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_123_in_drop_table1283 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_drop_table1285 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_table_name_in_drop_table1287 = new BitSet(new long[]{0x0000000000000002L,0x1000000000000000L});
    public static final BitSet FOLLOW_124_in_drop_table1290 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_131_in_drop_table1292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_create_index1312 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_create_index1314 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_121_in_create_index1317 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_index_name_in_create_index1319 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_create_index1321 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_table_name_in_create_index1323 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_create_index1325 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_list_in_create_index1327 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_create_index1329 = new BitSet(new long[]{0x0000000000000000L,0x003FE0020467F800L,0x0000000000000010L});
    public static final BitSet FOLLOW_132_in_create_index1332 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_IS_in_create_index1334 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_qualified_name_in_create_index1336 = new BitSet(new long[]{0x0000000000000000L,0x003FE0020467F800L});
    public static final BitSet FOLLOW_ora_configs_in_create_index1340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualified_name_in_index_name1355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_123_in_drop_index1373 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_121_in_drop_index1375 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_index_name_in_drop_index1377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_create_sequence1386 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_133_in_create_sequence1388 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_create_sequence1390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_123_in_drop_sequence1405 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_133_in_drop_sequence1407 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_drop_sequence1409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_134_in_comment1427 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_comment1430 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_table_comment_details_in_comment1434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_column_comment_details_in_comment1438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_table_comment_details1448 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_table_name_in_table_comment_details1450 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_IS_in_table_comment_details1452 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_STRING_in_table_comment_details1454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_135_in_column_comment_details1473 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_column_name_in_column_comment_details1475 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_IS_in_column_comment_details1477 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_STRING_in_column_comment_details1479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualified_name_in_column_name1496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_and_expression_in_expression1514 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_OR_in_expression1517 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_and_expression_in_expression1520 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_exclusive_or_expression_in_and_expression1531 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_AND_in_and_expression1534 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_exclusive_or_expression_in_and_expression1537 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_equality_expression_in_exclusive_or_expression1546 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_XOR_in_exclusive_or_expression1549 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_equality_expression_in_exclusive_or_expression1552 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_null_comparison_in_equality_expression1561 = new BitSet(new long[]{0x0000001C00000002L});
    public static final BitSet FOLLOW_set_in_equality_expression1564 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_null_comparison_in_equality_expression1577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relational_expression_in_null_comparison1587 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_IS_in_null_comparison1590 = new BitSet(new long[]{0x0000040000000020L});
    public static final BitSet FOLLOW_NOT_in_null_comparison1593 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_NULL_in_null_comparison1596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_in_expression_in_relational_expression1606 = new BitSet(new long[]{0x000001E000000002L});
    public static final BitSet FOLLOW_set_in_relational_expression1609 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_in_expression_in_relational_expression1626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_between_expression_in_in_expression1636 = new BitSet(new long[]{0x0000080000000022L});
    public static final BitSet FOLLOW_NOT_in_in_expression1639 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_IN_in_in_expression1642 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_in_expression1645 = new BitSet(new long[]{0x4000060000000FD0L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_value_list_in_in_expression1648 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_in_expression1650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additive_expression_in_between_expression1660 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_BETWEEN_in_between_expression1663 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_additive_expression_in_between_expression1666 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_AND_in_between_expression1668 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_additive_expression_in_between_expression1671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicative_expression_in_additive_expression1680 = new BitSet(new long[]{0x0004600000000002L});
    public static final BitSet FOLLOW_set_in_additive_expression1683 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_multiplicative_expression_in_additive_expression1696 = new BitSet(new long[]{0x0004600000000002L});
    public static final BitSet FOLLOW_unary_expression_in_multiplicative_expression1704 = new BitSet(new long[]{0x0003800000000002L});
    public static final BitSet FOLLOW_set_in_multiplicative_expression1707 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_unary_expression_in_multiplicative_expression1720 = new BitSet(new long[]{0x0003800000000002L});
    public static final BitSet FOLLOW_SUB_in_unary_expression1736 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_primary_in_unary_expression1739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_unary_expression1749 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_primary_in_unary_expression1752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primary_in_unary_expression1762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_primary1782 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_expression_in_primary1785 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_primary1787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_primary1798 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_arguments_in_primary1800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_value_in_primary1820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_arguments1833 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F40EL,0x00000000000002A2L});
    public static final BitSet FOLLOW_expression_in_arguments1836 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_68_in_arguments1839 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_expression_in_arguments1841 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_67_in_arguments1847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expression_list1863 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_expression_list1866 = new BitSet(new long[]{0x4000460000000FF0L,0x0E1FE0040441F406L,0x00000000000002A2L});
    public static final BitSet FOLLOW_expression_in_expression_list1869 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_name_in_qualified_name1878 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_136_in_qualified_name1881 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_qualified_name1884 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_value_in_value_list1893 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_value_list1896 = new BitSet(new long[]{0x4000060000000FD0L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_value_in_value_list1898 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_name_in_value1917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_value1921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_name_list1928 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_name_list1931 = new BitSet(new long[]{0x4000020000000F40L,0x0E1FE0040441F402L,0x00000000000002A2L});
    public static final BitSet FOLLOW_name_in_name_list1933 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENTIFIER_in_name1952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ora_key_in_name1956 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyword_in_name1960 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_QUOTED_NAME_in_name1964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_literal0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_keyword0 = new BitSet(new long[]{0x0000000000000002L});

}