/* Primary goal of this source file so far is to setup as much of the SlDL for
 * the family tree as possible before integrating the two source files.
 * Right now, I'm gonna try to get the contact cards setup.
 */

#define _CRT_SECURE_NO_WARNINGS
#include <SDL.h>
#include <SDL_ttf.h>
#include <SDL_image.h>
#include <stdio.h>
#include <string>
#include <sstream>

#define SCREEN_WIDTH 1530
#define SCREEN_HEIGHT 720

//Function prototypes
bool init();
bool loadMedia();
void close();

//Other
SDL_Window* gWindow = NULL;
SDL_Renderer* gRenderer = NULL;
TTF_Font* gFont = NULL;
SDL_Color textColor = { 0x0, 0x0, 0x0, 0xFF};



//Texture wrapper class	
class LTexture
	{
		public:
			//Initializes variables
			LTexture();
	
			//Deallocates memory
			~LTexture();

			//Loads image at specified path
			bool loadFromFile( std::string path );

			#ifdef _SDL_TTF_H
			//Creates image from font string
			bool loadFromRenderedText( std::string textureText, SDL_Color textColor );
			#endif

			//Deallocates texture
			void free();

			//Set color modulation
			void setColor( Uint8 red, Uint8 green, Uint8 blue );

			//Set blending
			void setBlendMode( SDL_BlendMode blending );

			//Set alpha modulation
			void setAlpha( Uint8 alpha );

			//Renders texture at given point
			void render( int x, int y, SDL_Rect* clip = NULL, double angle = 0.0, SDL_Point* center = NULL, SDL_RendererFlip flip = SDL_FLIP_NONE );

			//Gets image dimensions
			int getWidth();
			int getHeight();

		private:
			//The actual hardware texture
			SDL_Texture* mTexture;

		//Image dimensions
			int mWidth;
			int mHeight;
};

bool LTexture::loadFromFile( std::string path )
{
	//Get rid of preexisting texture
	free();

	//The final texture
	SDL_Texture* newTexture = NULL;

	//Load image at specified path
	SDL_Surface* loadedSurface = IMG_Load( path.c_str() );
	if( loadedSurface == NULL )
	{
		printf( "Unable to load image %s! SDL_image Error: %s\n", path.c_str(), IMG_GetError() );
	}
	
	else
	{
		//Color key image	
		SDL_SetColorKey( loadedSurface, SDL_TRUE, SDL_MapRGB( loadedSurface->format, 0, 0xFF, 0xFF ) );

		//Create texture from surface pixels
        newTexture = SDL_CreateTextureFromSurface( gRenderer, loadedSurface );
		if( newTexture == NULL )
		{
			printf( "Unable to create texture from %s! SDL Error: %s\n", path.c_str(), SDL_GetError() );
		}

		else
		{
			//Get image dimensions
			mWidth = loadedSurface->w;
			mHeight = loadedSurface->h;
		}

		//Get rid of old loaded surface
		SDL_FreeSurface( loadedSurface );
	}


	
	//Return success
	mTexture = newTexture;
	return mTexture != NULL;
}

#ifdef _SDL_TTF_H

	bool LTexture::loadFromRenderedText( std::string textureText, SDL_Color textColor )
	
{

		//Get rid of preexisting texture

		free();

	

	//Render text surface

		SDL_Surface* textSurface = TTF_RenderText_Solid( gFont, textureText.c_str(), textColor );

		if( textSurface == NULL )

		{

			printf( "Unable to render text surface! SDL_ttf Error: %s\n", TTF_GetError() );
	
	}

		else
	
	{

			//Create texture from surface pixels

		        mTexture = SDL_CreateTextureFromSurface( gRenderer, textSurface );

			if( mTexture == NULL )

		{

			printf( "Unable to create texture from rendered text! SDL Error: %s\n", SDL_GetError() );

		}

		else

		{

			//Get image dimensions

			mWidth = textSurface->w;

			mHeight = textSurface->h;

		}



		//Get rid of old surface

		SDL_FreeSurface( textSurface );

	}

	

	//Return success

	return mTexture != NULL;
	
}

#endif

void LTexture::free()

	{

		//Free texture if it exists

		if( mTexture != NULL )

		{

			SDL_DestroyTexture( mTexture );

			mTexture = NULL;

			mWidth = 0;

			mHeight = 0;

		}
	
}

void LTexture::setColor( Uint8 red, Uint8 green, Uint8 blue )
	
{

		//Modulate texture rgb
	
	SDL_SetTextureColorMod( mTexture, red, green, blue );
	
}

void LTexture::setBlendMode( SDL_BlendMode blending )
	
{

		//Set blending function

		SDL_SetTextureBlendMode( mTexture, blending );
	
}

void LTexture::setAlpha( Uint8 alpha )

	{

		//Modulate texture alpha

		SDL_SetTextureAlphaMod( mTexture, alpha );
	
}

void LTexture::render( int x, int y, SDL_Rect* clip, double angle, SDL_Point* center, SDL_RendererFlip flip )

	{

		//Set rendering space and render to screen
	
	SDL_Rect renderQuad = { x, y, mWidth, mHeight };

	

	//Set clip rendering dimensions

		if( clip != NULL )

		{
		
			renderQuad.w = clip->w;

			renderQuad.h = clip->h;

		}



		//Render to screen

		SDL_RenderCopyEx( gRenderer, mTexture, clip, &renderQuad, angle, center, flip );

	}

int LTexture::getWidth()

	{

		return mWidth;

	}

int LTexture::getHeight()

	{

		return mHeight;

	}


//Name class
class name
{
public:
	std::string first, last, text;
	bool extract();
	void clearContent() {first.clear(); last.clear(); text.clear();};
	bool compress();
};

bool name:: compress()
{
	bool success = true;
//	text = first + " " + last;
//	text.append(first.c_str(), " ");
	text.append(first.c_str());
	text.append(" ");
	text.append(last.c_str());
	if(text.c_str() == nullptr)
		success = false;
	return success;
}

bool name::extract()
{
	bool success = true;
	sscanf(text.c_str(),"%s %s", first, last); //I'm not sure if I should a '.c_str()' to the end of first and last
	if((first.c_str() == nullptr) || (last.c_str() == NULL)) // I'm not sure if this'll work because I'm converting the string to a c string
		success = false;
	return success;
}

//Date class
class date
{
public:
	int day, month, year;
	// For the date we use a different format because we have to deal with yyyy/mm/dd which makes things just a little more complicated
	std::ostringstream text;
	bool extract();
	void clearContent() {day = NULL; month = NULL; year = NULL;};
	bool compress();
};

bool date::compress()
{
	std::string temporary;
	bool success = true;
/*	text = year + "/" + month;
	temporary = text;
	text = temporary + "/";
	temporary = text;
	text = temporary + day;
*/
//	sprintf(text,"%d/%d/%d", year, month, day);
	text << year << "/" << month << "/" << day;

	if(text == NULL)
		success = false;
	return success;
}

bool date::extract()
{
	bool success = true;
//	sscanf(text,"%d/%d/%d", &year, &month, &day);

	if((year == NULL) || (month == NULL) || (day == NULL))
		success = false;
	return success;
}

//Enumeration for the different textures
enum contactTextures {name_e, spouseName_e, birth_e, death_e, totalContactTextures_e}contactTextures;

//Contact card Class
class contactCard 
{
public:
	contactCard(std::string);
	name name, spouseName;
	date birth, death, momBirth, dadBirth;
	int textureWidth, textureHeight;

//	std::string text;
	bool extract(std::string);
//	LTexture *texture; texture = new LTexture[totalContactTextures_e];
//	LTexture texture[totalContactTextures_e]; //This way, I can work with the individual textures like texture[myName] and then assign it and loadFromRenderedText and stuff. And then just use a for loop to make sure I get each of the contact textures
//	LTexture texture();	
	SDL_Texture* textureText;
	
	void clearContent() {name.clearContent(); spouseName.clearContent(); birth.clearContent(); death.clearContent(); momBirth.clearContent(); dadBirth.clearContent(); };
	bool loadTextures();
	bool loadLine(std::string variableText, std::string textureText, int rowNumber, SDL_Surface* textSurface, SDL_Texture* texture[]);

};

contactCard:: contactCard (std::string text)
{
	textureWidth = 0; textureHeight = 0;
	if( !(extract(text)) )
	{
		printf("\nError! Could not extract information from the line of text");
	}
	else if ( !(loadTextures()) )
	{
		printf("\nError! Could not load textures");
	}
	clearContent();
}
// Next up, write the extract and the loadtextures functions. 

bool contactCard::extract(std::string text)
{
	const int check = -1;
	bool success = true;
	//Note that the way text is going to come in is like momBirth dadBirth birth death name spouseName
	sscanf(text.c_str(),"%d/%d/%d %d/%d/%d %d/%d/%d %d/%d/%d %s %s %s %s",&momBirth.year, &momBirth.month, &momBirth.day, &dadBirth.year, &dadBirth.month, &dadBirth.day, &birth.year, &birth.month, &birth.day, &death.year, &death.month, &death.day, name.first.c_str(), name.last.c_str(), spouseName.first.c_str(), spouseName.last.c_str());
	if((momBirth.year == check) || (momBirth.month == check) || (momBirth.day == check) || (dadBirth.year == check) || (dadBirth.month == check) || (dadBirth.day == check) || (birth.year == check) || (birth.month == check) || (birth.day == check) || (death.year == check) || (death.month == check) || (death.day == check) || (name.first.c_str() == nullptr) || (name.last.c_str() == nullptr) || (spouseName.first.c_str() == nullptr) || (spouseName.last.c_str() == nullptr))
	{
			success = false;
			printf("\nError! Failed to extract info from main text line!");
	}

	//Now I compress that info
	if ( !name.compress() )
	{
		printf("\nFailed to compress name");
		success = false;
	}
	else if ( !spouseName.compress() )
	{
		printf("\nFailed to compress spouse's name");
		success = false;
	}
	else if ( !birth.compress() )
	{
		printf("\nFailed to compress brith date");
		success = false;
	}
	else if ( !death.compress() )
	{
		printf("\nFailed to compress death date");
		success = false;
	}
	else if ( !momBirth.compress() )
	{
		printf("Failed to compress mom's birth date");
		success = false;
	}
	else if ( !dadBirth.compress() )
	{
		printf("Failed to compress dad's birth date");
		success = false;
	}

	return success;
}

//The following loadLine function might not be needed anymore since the loadTextures function is being redone, preferrably without the need for the LTexture class
/*
bool contactCard:: loadLine(std::string variableText, std::string textureText, int textureNumber)
{
	bool success = true;
	//In this case variable text is the user input text
	textureText = textureText + variableText;
	if (textureText.c_str() == NULL) 
	{
		success = false;
		printf("\nError! Could not load line!");
	}
	else 
	{
		texture.loadFromRenderedText(textureText, textColor);
		texture.render(SCREEN_WIDTH/2, SCREEN_HEIGHT * textureNumber / totalContactTextures_e, 0, 0, 0, SDL_FLIP_NONE);
		textureText.clear(); variableText.clear();
	}
	return success;
}
*/

//The Following loadTextures function is being completely rewritten. Preferrably without the need to use the LTexture class.
/*
bool contactCard:: loadTextures()
{
	std::string textureText, variableText;
	bool success = true;

	//Note, underscore e stands is used to represent the data points in the enumeration

	for(int count = name_e; count < totalContactTextures_e; count++)
	{
		switch (count){

		case name_e:
			if(!(loadLine(name.text, "Name: ", count)))
			{
					success = false;
					printf("\nError! Failed to load line");
			}
			break;

		case spouseName_e:
			if(!(loadLine(spouseName.text, "Spouse's Name: ", count)))
			{
					success = false;
					printf("\nError! Failed to load line");
			}
			break;

		case birth_e:
			if(!(loadLine(birth.text.str(), "Date of Birth: ", count)))
			{
					success = false;
					printf("\nError! Failed to load line");
			}
			break;

		case death_e:
			if(!(loadLine(death.text.str(), "Date of Death: ", count)))
			{
				success = false;
				printf("\nError! Failed to load line");
			}
			break;
		}
	}

	return success;
}
*/

bool contactCard:: loadLine( std::string variableText, std::string textureText, int rowNumber, SDL_Surface* textSurface, SDL_Texture* texture[]) 
{
	bool success = true;
	SDL_Color bgColor = {0xFF, 0xFF, 0xFF, 0};
	textureText = textureText + variableText;
//	textSurface = TTF_RenderText_Blended(gFont, textureText.c_str(), textColor);
//	textSurface = TTF_RenderText_Blended_Wrapped(gFont, textureText.c_str(), textColor, 0);
	textSurface = TTF_RenderText_Shaded(gFont, textureText.c_str(), textColor, bgColor);

	if( textSurface == NULL )
	{
			printf( "\nUnable to render text surface! SDL_ttf Error: %s", TTF_GetError() );
			success = false;
	}
	else
	{
		texture[rowNumber] = SDL_CreateTextureFromSurface(gRenderer, textSurface);
		if (texture[rowNumber] == NULL)
			printf("\nUnable to create texture from surface");

		if((textSurface ->w) > textureWidth)
			textureWidth = textSurface -> w;
		textureHeight += textSurface -> h;


/*		else 
		{
			SDL_RenderCopy( gRenderer, texture[rowNumber], NULL, NULL);
			
		} */
	}
//	SDL_FreeSurface(textSurface);
	return success;
	
}

bool contactCard:: loadTextures()
{
	std:: string variableText;
	bool success = true;
	SDL_Surface* textSurface = NULL;
	SDL_Texture* texture[totalContactTextures_e];
	SDL_Rect contactRect[totalContactTextures_e];
	const int rectHeight = 150;

//	int textureWidth = 0; int textureHeight = 0;
	

	
	for (int count = name_e; count < totalContactTextures_e; count++)
	{
		switch (count)
		{
		case name_e:
			if(!(loadLine(name.text, "Name: ", count, textSurface, texture)))
			{
				printf("\nFailed to load name");
				success = false;
			}
			else
			{
//				textureWidth = textSurface ->w;
//				textureHeight = textSurface ->h;
//				contactRect[name_e]->h = textSurface->h;
				contactRect[name_e].h = rectHeight;
				contactRect[name_e].w = textureWidth;
				contactRect[name_e].x = 0;
				contactRect[name_e].y = 0;
				SDL_RenderCopy( gRenderer, texture[name_e], NULL, &contactRect[name_e]);
			}
			SDL_FreeSurface(textSurface);
			break;
			
		case spouseName_e:
			if(!(loadLine(spouseName.text, "Spouse's Name: ", count, textSurface, texture)))
			{
				printf("\nFailed to load spouse name");
				success = false;
			}
			else
			{
//				if((textSurface ->w) > textureWidth)
//					textureWidth = textSurface -> w;
//				textureHeight += textSurface -> h;
//				contactRect[spouseName_e]->h = textSurface->h;
				contactRect[spouseName_e].h = rectHeight;
				contactRect[spouseName_e].w = textureWidth;
				contactRect[spouseName_e].x = 0;
				contactRect[spouseName_e].y = (contactRect[name_e]. y) + (contactRect[name_e]. h);
				SDL_RenderCopy (gRenderer, texture[spouseName_e], NULL, &contactRect[spouseName_e]);
			}
			SDL_FreeSurface(textSurface);
			break;

		case birth_e: 
			if(!(loadLine(birth.text.str(), "Date of birth: ", count, textSurface, texture)))
			{
				printf("\nFailed to load date of birth");
				success = false;
			}
			else
			{
//				if((textSurface ->w) > textureWidth)
//					textureWidth = textSurface -> w;
//				textureHeight += textSurface -> h;
//				contactRect[birth_e]->h = textSurface->h;
				contactRect[birth_e].h = rectHeight;
				contactRect[birth_e].w = textureWidth;
				contactRect[birth_e].x = 0;
				contactRect[birth_e].y = (contactRect[spouseName_e].y) + (contactRect[spouseName_e].h);
				SDL_RenderCopy (gRenderer, texture[birth_e], NULL, &contactRect[birth_e]);
			}
			SDL_FreeSurface(textSurface);
			break;

		case death_e:
			if(!(loadLine(death.text.str(), "Date of death: ", count, textSurface, texture)))
			{
				printf("\nFailed to load date of death");
				success = false;
			}
			else
			{
//				if((textSurface ->w) > textureWidth)
//					textureWidth = textSurface -> w;
//				textureHeight += textSurface -> h;
//				contactRect[death_e]->h = textSurface->h;
				contactRect[death_e].h = rectHeight;
				contactRect[death_e].w = textureWidth;
				contactRect[death_e].x = 0;
				contactRect[death_e].y = (contactRect[birth_e].y) + (contactRect[birth_e].h);
				SDL_RenderCopy (gRenderer, texture[death_e], NULL, &contactRect[death_e]);
			}
			SDL_FreeSurface(textSurface);
			break;
		} 
	}

	

	return success;
}

bool init()
{
	bool success = true;

	//Initialize SDL
	if( SDL_Init( SDL_INIT_EVERYTHING ) < 0 )
	{
		printf("\nSDL could not initialize! SDL Error: %s", SDL_GetError() );
		success = false;
	}
	else
	{
		//Set texture filtering to linear
		if( !SDL_SetHint ( SDL_HINT_RENDER_SCALE_QUALITY, "1" ) )
		{
			printf( "\nWarning: Linear texture filtering not enabled!" );
		}

		//Create Window
		gWindow = SDL_CreateWindow( "Family Tree Program (In Progress)", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN );
		if (gWindow == NULL)
		{
			printf("\nWindow could not be created! SDL Error: %s", SDL_GetError() );
			success = false;
		}
		else
		{
			//Create vsynced renderer for window
			gRenderer = SDL_CreateRenderer( gWindow, -1, SDL_RENDERER_ACCELERATED | SDL_RENDERER_PRESENTVSYNC );
			if ( gRenderer == NULL )
			{
				printf("\nRenderer could not be created! SDL Error: %s", SDL_GetError() );
				success = false;
			}
			else
			{
				//Initialize renderer colour
				SDL_SetRenderDrawColor( gRenderer, 0xFF, 0xFF, 0xFF, 0xFF );

				//Initialize PNG Loading
				int imgFlags = IMG_INIT_PNG;
				if( !(IMG_Init (imgFlags) & imgFlags ) )
				{
					printf("\nSDL Image could not initialize! SDL_Image Error: %s", IMG_GetError() );
					success = false;
				}

				//Initialize SDL_ttf
				if( TTF_Init() == -1 )
				{
					printf( "SDL_ttf could not initialize! SDL_ttf Error: %s", TTF_GetError() );
					success = false;
				}
			}
		}
		return success;
	}
}

bool loadMedia()
{
	bool success = true;

	//Open the font
	gFont = TTF_OpenFont("OldSchoolClass 3001.ttf", 150);
	if(gFont == NULL)
	{
		printf("Error! Failed to load the font. SDL_ttf Error: %s",TTF_GetError());
		success = false;
	}

	return success;
}

void close()
{
	//Free global font
	TTF_CloseFont( gFont );
	gFont = NULL;

	//Destroy window
	SDL_DestroyRenderer( gRenderer );
	SDL_DestroyWindow( gWindow );
	gWindow = NULL;
	gRenderer = NULL;

	//Quit SDL Subsystems
	TTF_Quit();
	IMG_Quit();
	SDL_Quit();
}

//Next up, write yourself a main function and check to see if I have succesfully completed my task
int main( int argc, char* args[])
{
	if (!init())
	{
		printf("\nError! Could not initialize SDL and/or other extension libraries");
		return 1;
	}
	else if (!loadMedia())
	{
		printf("\nError! Could not load media");
		return 1;
	}
	else
	{
		std::string sampleContact = "1980/09/20 1972/08/08 1998/08/17 0/0/0 Penjani Chavula No Spouse";
		bool quit = false;
		//Event Handler
		SDL_Event e;

		contactCard member( sampleContact );

		while (!quit)
		{
			while (SDL_PollEvent( &e ) != 0 )
			{
				if (e.type == SDL_QUIT)
					quit = true;
			}
			SDL_RenderPresent( gRenderer );
		}

		close();
		return 0;
	}

}

//Ok, next order of business is to resize the textures/surfaces. One way of doing this seems to get SDL2_gfx to work and use this extension library
/*
 * Right now, the current roadblock is that I need to be able to resize textures once they have been created. One method of doing this is using an extension
 * library called SDL2_gfx. However, to install this library, I need to use 'makefiles' which I do not currently understand. Hencee I've been trying to see
 * if I can install it some other way.
 */