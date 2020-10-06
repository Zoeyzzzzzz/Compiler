#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<ctype.h>

char c;
char token[500], line[1001];
//now代表现在进行的字符位置
//isContinue代表是否继续扫描
//token_now代表当前单词字符串的末位置
int now = 0, isContinue=0, token_now=0;

void getChar(){
	c = line[now++];
}

void cat(){
	token[token_now++] = c;
}

int isLetter(){
	if(isupper(c)||islower(c)) return 1;
	else return 0;
}

int isDigit(){
	if(isdigit(c)) return 1;
	else return 0;
}

void unGetch(){
	now--;
}

void reserve(){
	//非0是保留字和类别
	//0是标识符
	if(strcmp(token, "BEGIN") == 0) printf("BEGIN");
	else if(strcmp(token, "END") == 0) printf("END");
	else if(strcmp(token, "FOR") == 0) printf("FOR");
	else if(strcmp(token, "DO") == 0) printf("DO");
	else if(strcmp(token, "IF") == 0) printf("IF");
	else if(strcmp(token, "THEN") == 0) printf("THEN");
	else if(strcmp(token, "ELSE") == 0) printf("ELSE");
	else printf("Ident(%s)\n", token);
}

int atoiNew(){
	int num = 0;
	for(int i=0; i<token_now; i++){
		num *= 10;
		num += token[token_now] - '0';
	}
	return num;
}

void error(){
	isContinue = 1;
	printf("Unknown\n");
}


void getNbc(){
	while(line[now]!='\0' && isContinue==0){
		memset(token,'\0',sizeof(token));
		token_now = 0;
		c = '\0';

		getChar();
		//如果是空白字符，继续读下一个
    	while(c==' ' || c=='\t' || c=='\n' || c=='\r') getChar();

    	//如果是字母
    	if(isLetter()){               
    		while(isLetter() || isDigit()){
	    		cat();
		    	getChar();
	    	}
	    	unGetch();
	    	reserve(); 
    	}

    	//如果是数字
    	else if(isDigit()){
    		while(isDigit()){
    			cat();
    			getChar();
    		}
	    	unGetch();
	    	printf("Int(%d)\n", atoiNew());
    	}

    	//如果是单字符分界符
    	else if(recent=='+') printf("Plus\n");
    	else if(recent=='*') printf("Star\n");
    	else if(recent==',') printf("Comma\n");
    	else if(recent=='(') printf("LParenthesis\n");
    	else if(recent==')') printf("RParenthesis\n");

    	//如果是冒号状态，分下一个是等号还是非等号
    	else if(recent==':'){
    		//继续读下一个
    		getChar();
    		//如果是等号
    		if(c == '=') printf("Assign\n");
    		//如果不是等号
    		else{
	           printf("Colon\n");
	           unGetch();
    		} 
		
    	}
     	
    	else if(recent=='\0') break;
      	else error();
	}
}



int main(int argc, char *argv[]){
	FILE *fp = fopen(argv[1],"r");
	if(fp == NULL) printf("error"); 

	while(fgets(txt,1000,fp)!=NULL && isContinue==0){
		now = 0;
		getNbc();
		memset(line,'\0',sizeof(line));
	}

	fclose(fp);
	return 0;
}
