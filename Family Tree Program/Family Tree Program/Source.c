/* This program is meant to represent a family tree. Contact names will be provided to the program
 * and we can manipulate an external text file for storing the information
 */

#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX 80 //Maximum number of characters in a name
#define REFERENCE_NUMBER 9999 //Used once at the beginning of the sort function
#define DATE 3 // Number of elements in the date of birth integer array
#define CHAR_DATE 11 // Number of elements in the date of birth character array
#define YEAR 0 // For any of the date arrays
#define MONTH 1 // For any of the date arrays
#define DAY 2 // For any of the date arrays
#define DOB 1 // Date of  Birth, used in the input_date function
#define DOD 2 // Date of Death
#define CO_DOB 3 // Child of [Date of Birth]
#define FILE_NAME "Family Tree Info.txt"
#define COMPANION_FILE "Family Tree Companion.txt"
#define TRUE 1
#define FALSE 0

 //Structure Definition
 struct contact {
	 char name[MAX],text[MAX];
	 int date_of_birth[DATE];
	 int date_of_death[DATE];
	 int child_of_dob[DATE];
 } member;

 struct name {
		char text[MAX];
		char first[MAX];
		char last[MAX];
	}name_input;

 struct scanned {
	char text[MAX];
	int birthyear, birthmonth, birthday,deathyear, deathmonth, deathday,co_year,co_month,co_day; //'co' stands for child of
	struct name name;
	} scanned_text;

 //The above two structures have been moved from the search function so that I can use them in particular in the modify function

 void input_date(int identifier); //Function prototype for inputting the date. 
 void add_member(void); //Function prototype for adding member
 int modify_member(void);

int main(void) {
	int decision; // User decision.
	
	do {
		do{
			printf("\n\nWhat would you like to do?\n1) View Family Tree\n2) Add to Family Tree\n3) Modify current member of family tree\n 4) Exit\n Enter a number and press enter: ");
			scanf("%d",&decision);
			if (decision != 1 && decision != 2 && decision != 3 && decision != 4)
				printf("\nError!! The number you have entered is not an option. Please try again\n");
		}
		while (decision != 1 && decision != 2 && decision != 3 && decision != 4);
	
		switch (decision) {
	case 1:
		// Function to open family tree
		break;
	case 2:
		add_member(); // Function to add to family tree
		break;
	case 3:
		modify_member(); // Function to modify current member of family tree
		break;
	default:
		break;
	}
	}
	while (decision != 4);

	return 0;
}

void add_member(void) {
	int identifier; // This identifier is used in the for loop to identify whether the date we're adding is BOD, DOD, or CO_BOD
	FILE* data;
	data = fopen(FILE_NAME,"a");
	printf("\nWe shall now input the relevant information for this family member\n");
	
	printf("\nPlease enter the name of this family member: ");
	fgets(member.name,2,stdin);
	fgets(member.name,MAX,stdin);
	printf("\nThe name is %s",member.name);
	
	for(identifier = 1; identifier <= 3; identifier++){
		input_date(identifier);
	}

	fprintf(data,"\n%d/%d/%d %d/%d/%d %d/%d/%d %s",member.child_of_dob[YEAR],member.child_of_dob[MONTH],member.child_of_dob[DAY],member.date_of_birth[YEAR],member.date_of_birth[MONTH],member.date_of_birth[DAY],member.date_of_death[YEAR],member.date_of_death[MONTH],member.date_of_death[DAY],member.name);
	fclose(data);

}

void input_date(int identifier) {
	switch (identifier) {
	case DOB:
		printf("\nYear of Birth: ");
		scanf("%d",&member.date_of_birth[YEAR]);

		printf("\nMonth of Birth (In number form, not as letters): ");
		scanf("%d",&member.date_of_birth[MONTH]);

		printf("\nDay of Birth: ");
		scanf("%d",&member.date_of_birth[DAY]);

		break;

	case DOD:
		printf("\nIf the member is still alive, please enter 0000 for year of death, 00 for the month, and 00 for the day\n");
		printf("\nYear of Death: ");
		scanf("%d",&member.date_of_death[YEAR]);

		printf("\nMonth of Death: ");
		scanf("%d",&member.date_of_death[MONTH]);

		printf("\nDay of Death: ");
		scanf("%d",&member.date_of_death[DAY]);

		break;

	case CO_DOB:
		printf("\nNow you must enter the date of birth of the parent of this member.\n");
		
		printf("\nYear of Birth: ");
		scanf("%d",&member.child_of_dob[YEAR]);

		printf("\nMonth of Birth: ");
		scanf("%d",&member.child_of_dob[MONTH]);

		printf("\nDay of Birth: ");
		scanf("%d",&member.child_of_dob[DAY]);

		printf("\n\n DONE!");

		break;
	}
}


// Later on might want to include option to search by child_of_date_of_birth in which case options should be listed
	/* Idea with 'scanned_text' variable is to: 
	 * scan each line in the text document and it store it into the first row of 'scanned_text' using fgets function
	 * use sscanf function to then separate sections of first row, into 2nd 3rd and 4th row
	 * i.e. date of birth gets put into second row, name into 4th row
	 * After sorting out, I can compare the user input information with the corresponding scanned text information and see if it is a match
	 * This way, I also have all of that contact's information if it  is a match
	 */

int search_for_member(void) {
	//char name[MAX], date_of_birth[MAX], date_of_death[MAX], scanned_text[3][MAX]; 
	
	int decision; //User decision, for deciding how user wants to identify family member to modify
	int user_decision;
	FILE* data;
	int check_result = FALSE;
	struct date {
		char text[MAX];
		int year, month, day;
	}birth,death;

// I have proceeded to initialize the following two structures globally
/*	struct name {
		char text[MAX];
		char first[MAX];
		char last[MAX];
	}name_input;

	struct scanned {
		char text[MAX];
		int birthyear, birthmonth, birthday,deathyear, deathmonth, deathday,co_year,co_month,co_day; //'co' stands for child of
		struct name name;
	} scanned_text;
*/

	do {
		printf("Would you like to specify by \n1) Date of birth\n2) Date of death\n 3) Name\nInput the number corresponding to the option and press enter. After doing this, input the desired information\n");
		scanf("%d",&decision);
		if (decision != 1 && decision != 2 && decision != 3)
				printf("\nError!! The number you have entered is not an option. Please try again\n");
	}
	while (decision != 1 && decision != 2 && decision != 3);

	printf("\nPlease input the member's ");

	switch (decision) {
	case 1:
		printf("date of birth in the format yyyy/mm/dd: ");
		fgets(birth.text,CHAR_DATE,stdin);
		fgets(birth.text,CHAR_DATE,stdin);
		sscanf(birth.text,"%d/%d/%d",&birth.year,&birth.month,&birth.day);
		break;

	case 2:
		printf("\ndate of death in the format yyyy/mm/dd: ");
		fgets(death.text,CHAR_DATE,stdin);
		fgets(death.text,CHAR_DATE,stdin);
		sscanf(death.text,"%d/%d/%d",&death.year,&death.month,&death.day);
		break;

	case 3:
		printf("\nName: ");
		fgets(name_input.text,MAX,stdin);
		fgets(name_input.text,MAX,stdin);
		sscanf(name_input.text,"%s %s",name_input.first,name_input.last);
		break;
	}

	data = fopen(FILE_NAME, "r");

//	while ((fgets(scanned_text.text,MAX,data)) != NULL) {
	while (fgets(scanned_text.text,MAX,data)) {
//		fgets(scanned_text.text,MAX,data); 
		sscanf(scanned_text.text,"%d/%d/%d %d/%d/%d %d/%d/%d %s %s",&scanned_text.co_year,&scanned_text.co_month,&scanned_text.co_day,&scanned_text.birthyear,&scanned_text.birthmonth,&scanned_text.birthday,&scanned_text.deathyear,&scanned_text.deathmonth,&scanned_text.deathday,scanned_text.name.first,scanned_text.name.last);
		switch (decision) {
		case 1:
			if(birth.year == scanned_text.birthyear && birth.month == scanned_text.birthmonth && birth.day == scanned_text.birthday) 
				check_result = TRUE;			
			break;

		case 2:
			if(death.year == scanned_text.deathyear && death.month == scanned_text.deathmonth && death.day == scanned_text.deathday)
				check_result = TRUE;
			break;

		case 3:
//			if((name_input.first == scanned_text.name.first) && (name_input.last == scanned_text.name.last))
// To check if two strings are equal, we use strcmp(char *, char *) and it returns 0 if the strings are equal
			if((strcmp(name_input.first,scanned_text.name.first) == 0) && (strcmp(name_input.last,scanned_text.name.last) == 0))
				check_result = TRUE;
			break;
		}
		if(check_result == TRUE)
			break;
	}

	fclose(data);

	if(check_result == FALSE) {
		printf("\nError, the contact was not found.\n");
		return FALSE;
	}

	do {
		printf("\nIs this the contact:\nName: %s %s\nDate of birth: %d/%d/%d\nDate of death: %d/%d/%d\n(Child of)Date of birth: %d/%d/%d\n\n",scanned_text.name.first,scanned_text.name.last,scanned_text.birthyear,scanned_text.birthmonth,scanned_text.birthday,scanned_text.deathyear,scanned_text.deathmonth,scanned_text.deathday,scanned_text.co_year,scanned_text.co_month,scanned_text.co_day);
	printf("\n1) Yes\n 2) No\n");
//	getchar();
	scanf("%d",&user_decision);
	if(user_decision != 1 && user_decision != 2)
		printf("Error!! The number you have entered is invalid, please try again");
	}
	while(user_decision != 1 && user_decision !=2);

	if (user_decision == 2) {
		printf("If you would like to try again, please go back to the main menu and select 'modify member'");
		getchar();
		return FALSE;
	}

		//Now we copy the scanned text information into the member contact structure
		strcpy(scanned_text.name.text,scanned_text.name.first);
		strcat(scanned_text.name.text, " ");
		strcat(scanned_text.name.text,scanned_text.name.last);
		strcpy(member.name,scanned_text.name.text);
	
		member.child_of_dob[YEAR] = scanned_text.co_year;
		member.child_of_dob[MONTH] = scanned_text.co_month;
		member.child_of_dob[DAY] = scanned_text.co_day;
		member.date_of_birth[YEAR] = scanned_text.birthyear;
		member.date_of_birth[MONTH] = scanned_text.birthmonth;
		member.date_of_birth[DAY] = scanned_text.birthday;
		member.date_of_death[YEAR] = scanned_text.deathyear;
		member.date_of_death[MONTH] = scanned_text.deathmonth;
		member.date_of_death[DAY] = scanned_text.deathday;

		//IF CURRENT TEST DOES NOT WORK, I'M GONNA HAVE TO MANUALLY STRCAT THE MEMBER'S INFO INTO THE SCANNED_TEXT.TEXT
	
	return TRUE;
}

/* Ok, I won't be allowed to modify data within a file without deleting everything else, So the plan now is to store all of the data into another file
 * excluding the membr to modified, rewriting the data from the other file back into the main file, and adding in the modified the member
 */

int modify_member(void) {
	int check_search;
	char current_member[MAX],member_to_copy[MAX];
	FILE *data,*companion;
	
	printf("\nYou have chosen to modify an existing family member. First we must identify which member specifically you would like to modify\n");
	check_search = search_for_member(); // Note that the searched member is stored in the contact  member structure
	if(check_search == FALSE)
		return FALSE;

	//Now, copy the searched member into the current member char variable
	strcpy(current_member,scanned_text.text);

	//Now we write all the data from the main file to the companion file excluding the current member
	data =  fopen(FILE_NAME,"r");
	companion = fopen(COMPANION_FILE, "w");

	// Good point to check if the file has opened succesfully

	while(fgets(member_to_copy,MAX,data)) {
//		if(fgets(member_to_copy,MAX,data) != current_member) // PERHAPS TRY USING STRCMP
		if((strcmp(member_to_copy,current_member) != 0) && (strcmp(member_to_copy,"\n") != 0)) // might have to modify second logic statement to "\n\0" but I'm not sure yet
			fprintf(companion,"%s\n",member_to_copy);
	}

	fclose(data);
	fclose(companion);

	//Now we rewrite the information from the companion file to the main file, since we don't have the member to modify anymore
	data = fopen(FILE_NAME,"w");
	companion = fopen(COMPANION_FILE,"r");

	// Good point to check if the file has opened succesully

	while(fgets(member_to_copy,MAX,companion)) {
		if(strcmp(member_to_copy,"\n") != 0) 
			fprintf(data,"%s\n",member_to_copy);
	}

	fclose(data);
	fclose(companion);

	add_member();

	return TRUE;
} //MUST FIX MODIFY MEMBER FUNCTION, AT THE MOMENT IT'S NOT DELETING THE MEMBER TO BE MODIFIED

//Now to write a function that sorts the information by child of date of birth

void sort(void) {
	FILE *data, *companion;
	int member_count, counter;
	struct scanned reference; //reference structure is also where I'm storing the oldest member and using them for reference
	struct dates {
		int co_year,co_month,co_day,birthyear,birthmonth,birthday;
	} oldest;
	oldest.birthday = 0; oldest.birthmonth = 0; oldest.birthyear = 0; oldest.co_day = 0; oldest.co_month = 0; oldest.co_year = 0;
	reference.birthday = REFERENCE_NUMBER; reference.birthmonth = REFERENCE_NUMBER; reference.birthyear = REFERENCE_NUMBER; reference.co_day = REFERENCE_NUMBER; reference.co_month = REFERENCE_NUMBER; reference.co_year = REFERENCE_NUMBER;


	member_count = 0;
	data = fopen(FILE_NAME,"r");
	while (fgets(scanned_text.text,MAX,data)) {
		if(strcmp(scanned_text.text,"\n") != 0)
			member_count++; // This is the portion where we count how many members are listed in the document
	}
	fclose(data);

	companion = fopen(COMPANION_FILE,"w");

	for(counter = 1; counter <= member_count; counter++) {
		data = fopen(FILE_NAME,"r");

// Now identify oldest member
		while (fgets(scanned_text.text, MAX, data)){
			if(strcmp(scanned_text.text,"\n") != 0) {
				sscanf(scanned_text.text,"%d/%d/%d %d/%d/%d %d/%d/%d %s %s",&scanned_text.co_year,&scanned_text.co_month,&scanned_text.co_day,&scanned_text.birthyear,&scanned_text.birthmonth,&scanned_text.birthday,&scanned_text.deathyear,&scanned_text.deathmonth,&scanned_text.deathday,scanned_text.name.first,scanned_text.name.last);
				if((scanned_text.co_year < reference.co_year) && (scanned_text.co_year > oldest.co_year)){ //reference is for when we're trying to identify the oldest out of the group, the oldest.co_year helps us to exclude counting the members that have already been copied. 
					strcpy(reference.text,scanned_text.text);
					reference.co_year = scanned_text.co_year; reference.co_month = scanned_text.co_month; reference.co_day = scanned_text.co_day; reference.birthyear = scanned_text.birthyear; reference.birthmonth = scanned_text.birthmonth; reference.birthday = scanned_text.birthday; reference.deathyear = scanned_text.deathyear; reference.deathmonth = scanned_text.deathmonth; reference.deathday = scanned_text.deathday;
					strcpy(reference.name.first,scanned_text.name.first);
					strcpy(reference.name.last,scanned_text.name.last);

				}

				else if((scanned_text.co_year == reference.co_year) && (scanned_text.co_month < reference.co_month) && (scanned_text.co_year == oldest.co_year) && (scanned_text.co_month < oldest.co_month)) { //Possible source of error with the 'oldest' logic statements
					strcpy(reference.text,scanned_text.text);
					reference.co_year = scanned_text.co_year; reference.co_month = scanned_text.co_month; reference.co_day = scanned_text.co_day; reference.birthyear = scanned_text.birthyear; reference.birthmonth = scanned_text.birthmonth; reference.birthday = scanned_text.birthday; reference.deathyear = scanned_text.deathyear; reference.deathmonth = scanned_text.deathmonth; reference.deathday = scanned_text.deathday;
					strcpy(reference.name.first,scanned_text.name.first);
					strcpy(reference.name.last,scanned_text.name.last);
				}

				else if((scanned_text.co_year == reference.co_year) && (scanned_text.co_month == reference.co_month) && (scanned_text.co_year == oldest.co_year) && (scanned_text.co_month == oldest.co_month) && (scanned_text.co_day < reference.co_day) && (scanned_text.co_day < oldest.co_day)) {
					strcpy(reference.text,scanned_text.text);
					reference.co_year = scanned_text.co_year; reference.co_month = scanned_text.co_month; reference.co_day = scanned_text.co_day; reference.birthyear = scanned_text.birthyear; reference.birthmonth = scanned_text.birthmonth; reference.birthday = scanned_text.birthday; reference.deathyear = scanned_text.deathyear; reference.deathmonth = scanned_text.deathmonth; reference.deathday = scanned_text.deathday;
					strcpy(reference.name.first,scanned_text.name.first);
					strcpy(reference.name.last,scanned_text.name.last);
				}

				else if((scanned_text.co_year == reference.co_year) && (scanned_text.co_month == reference.co_month) && (scanned_text.co_day == reference.co_day) && (scanned_text.co_year == oldest.co_year) && (scanned_text.co_month == oldest.co_month) && (scanned_text.co_day == oldest.co_day)) { //Then they have the same parent
					if((scanned_text.birthyear < reference.birthyear) && (scanned_text.birthyear > oldest.birthyear)){ //now we check the actual date of birth and see how it compares 
						strcpy(reference.text,scanned_text.text);
						reference.co_year = scanned_text.co_year; reference.co_month = scanned_text.co_month; reference.co_day = scanned_text.co_day; reference.birthyear = scanned_text.birthyear; reference.birthmonth = scanned_text.birthmonth; reference.birthday = scanned_text.birthday; reference.deathyear = scanned_text.deathyear; reference.deathmonth = scanned_text.deathmonth; reference.deathday = scanned_text.deathday;
						strcpy(reference.name.first,scanned_text.name.first);
						strcpy(reference.name.last,scanned_text.name.last);

					}

					else if((scanned_text.birthyear == reference.birthyear) && (scanned_text.birthmonth < reference.birthmonth) && (scanned_text.birthyear == oldest.birthyear) && (scanned_text.birthmonth < oldest.birthmonth)) {
						strcpy(reference.text,scanned_text.text);
						reference.co_year = scanned_text.co_year; reference.co_month = scanned_text.co_month; reference.co_day = scanned_text.co_day; reference.birthyear = scanned_text.birthyear; reference.birthmonth = scanned_text.birthmonth; reference.birthday = scanned_text.birthday; reference.deathyear = scanned_text.deathyear; reference.deathmonth = scanned_text.deathmonth; reference.deathday = scanned_text.deathday;
						strcpy(reference.name.first,scanned_text.name.first);
						strcpy(reference.name.last,scanned_text.name.last);
					}

					else if((scanned_text.birthyear == reference.birthyear) && (scanned_text.birthmonth == reference.birthmonth) && (scanned_text.birthyear == oldest.birthyear) && (scanned_text.birthmonth == oldest.birthmonth) && (scanned_text.birthday < reference.birthday) && (scanned_text.birthday < oldest.birthday)) {
						strcpy(reference.text,scanned_text.text);
						reference.co_year = scanned_text.co_year; reference.co_month = scanned_text.co_month; reference.co_day = scanned_text.co_day; reference.birthyear = scanned_text.birthyear; reference.birthmonth = scanned_text.birthmonth; reference.birthday = scanned_text.birthday; reference.deathyear = scanned_text.deathyear; reference.deathmonth = scanned_text.deathmonth; reference.deathday = scanned_text.deathday;
						strcpy(reference.name.first,scanned_text.name.first);
						strcpy(reference.name.last,scanned_text.name.last);
					}
				}
			}
		} // At this point, the person in the reference structure is the oldest person in the group. Now I must put their info onto the companion, and adjust the 'oldest' dates.
		oldest.birthday = reference.birthday; oldest.birthmonth = reference.birthmonth; oldest.birthyear = reference.birthyear; oldest.co_day = reference.co_day; oldest.co_month = reference.co_month; oldest.co_year = reference.co_year;
		fprintf(companion,"%d/%d/%d %d/%d/%d %d/%d/%d %s %s\n",reference.co_year,reference.co_month,reference.co_day,reference.birthyear,reference.birthmonth,reference.birthday,reference.deathyear,reference.deathmonth,reference.deathday,reference.name.first,reference.name.last);
		fclose(data);
	} // end of for loop
	fclose(companion); //Don't forget to rewrite the sorted info in the companion file onto the main file
}