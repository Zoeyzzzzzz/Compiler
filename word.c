#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<ctype.h>

char txt[1000];
int now;
 
char token[300];
int array;

int num;

char recent; 

int type;

int go;


void clearToken(){
	memset(token,'\0',sizeof(token));
	array=0;
	recent='\0';
	type=0;
} 


void getchar1(){
	recent=txt[now];
	now++; 
} 

void retract(){
	now--;
} 


void catToken(){
	token[array]=recent;
	array++;
} 


int isSpace(){
	if(recent==' '||recent=='\t'||recent=='\n'||recent=='\r'){
		return 1;
	}
	else return 0;
} 


int isLetter(){
	if(isupper(recent)||islower(recent)) return 1;
	return 0;
} 

int isDigit(){
	if(isdigit(recent)) return 1;
	return 0;
} 


int reserver(){
	if(!strcmp(token,"BEGIN")) return 1;
	else if(!strcmp(token,"END")) return 2;
	else if(!strcmp(token,"FOR")) return 3;
	else if(!strcmp(token,"IF")) return 4;
	else if(!strcmp(token,"THEN")) return 5;
	else if(!strcmp(token,"ELSE")) return 6;
	else return 0;
} 


int transNum(){
	int number=0;
	int i=0;
	for(i=0;i<array;i++){
		number*=10;
		number=number+token[i]-'0';
	}
	return number;
} 


void error(){
	printf("Unknown\n");
	go=1;
}

void chu(){
	switch(type){
		case 1:
			printf("Begin\n");
			break;
		case 2:
			printf("End\n");
			break;
		case 3:
			printf("For\n");
			break;
		case 4:
			printf("If\n");
			break;
		case 5:
			printf("Then\n");
			break;
		case 6:
			printf("Else\n");
			break;	
		case 7:
			printf("Ident(%s)\n",token);
			break;	
		case 8:
			printf("Int(%d)\n",num);
			break;	
		case 9:
			printf("Colon\n");
			break;
		case 10:
			printf("Plus\n");
			break;
		case 11:
			printf("Star\n");
			break;
		case 12:
			printf("Comma\n");
			break;
		case 13:
			printf("LParenthesis\n");
			break;
		case 14:
			printf("RParenthesis\n");
			break;
		case 15:
			printf("Assign\n");
			break;
		default:
			break;	
	}
}

void getsym(){
	while(txt[now]!='\0'&&go==0){
		clearToken();
		getchar1();
    	while(isSpace()) getchar1();
    	if(isLetter()){               
    		while(isLetter()||isDigit()){
	    		catToken();
		    	getchar1();
	    	}
	    	retract();
	    	int end=reserver();  
	    	if(end==0) {
	    		type=7; 
	    	}
	    	else type=end; 
    	} 
    	else if(isDigit()){
    		while(isDigit()){
    			catToken();
    			getchar1();
    		}
	    	retract();
	    	num=transNum();
    		type=8;
    	}
    	else if(recent==':'){
    		getchar1();
    		if(recent=='=') type=15; 
    		else{
	           type=9; 	
	           retract();
    		} 
		
    	}
     	else if(recent=='+') type=10;
    	else if(recent=='*') type=11;
    	else if(recent==',') type=12;
    	else if(recent=='(') type=13;
    	else if(recent==')') type=14;
    	else if(recent=='\0') break;
      	else error();
      	
    	chu();
	}

}
 

int main(int argc, char *argv[]){

	FILE *fp = fopen(argv[1],"r");
	if(fp==NULL) printf("error"); 
	while(fgets(txt,997,fp)!=NULL&&go==0){
		now=0;
		getsym();
		memset(txt,'\0',sizeof(txt));
	}
	fclose(fp);
	return 0;
}
