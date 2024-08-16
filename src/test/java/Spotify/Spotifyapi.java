package Spotify;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class Spotifyapi {

   public String token ="BQA-7dRGPfa3yIeskSWw0JGTcKgWcI3oUIg9FI4EeVbBI3MkpqutfpDfQwIOmRjZm6BzEsPvtDtkUbBj-mI-2S8beJvNFgTKGTSJm8Q0oseKs3Afk7JwcrncHZQGFdKtua7JCXmM8ZwsQaziRrQgvRf0uADlWpPd8BKTzVjgKaWfJai5Mkb9xIAqeTBuvVSNdTqVd8wkCY98nYW0_K1Fd8mkkJJUINVDixomNBYwyRuLnCMm4_aTS0YFym-WOsdINNgsKkKhwIcau756CJ0f-P3K7BoLwmZvHExll-sLqRvRa5B0NnKd9Ec_UUGe7RsoZgnmuRz-pQ1m_4G3hOTN";


   String userid;
   String artistid;
   String episodeid;
   String showid;
   String albumid;
   String audiobookid;
   String playlistid="0WioKBo6Sf6JLGz42Pr4Fv";
   String userplaylistid= "37i9dQZF1DX0XUfTFmNBRM";


   //--------------------------------------------------------User--------------------------------------------------

    @Test(priority = 1)
    public void GetCurrentUsersProfile(){
     Response res = given()
             .header("Accept","*/*")
             .header("Authorization","Bearer " +token)
             .when()
             .get("https://api.spotify.com/v1/me");
        res.prettyPrint();
        userid=res.path("id");
       res.then().statusCode(200);
    }

    @Test
    public void GetUsersTopItems(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/top/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 3)
    public void FollowPlaylist(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .put("https://api.spotify.com/v1/playlists/"+ userplaylistid +"/followers");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 2)
    public void GetUsersProfile(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/users/" + userid);
        res.prettyPrint();
        res.then().statusCode(200);
        String msg=res.path("display_name");
        Assert.assertEquals(msg,"Anjali");
    }

    @Test(priority = 7)
    public void GetFollowedArtists(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/following?type=artist");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 8)
    //5f4QpKfy7ptCHwTqspnSJI
    public void FollowArtistsorUsers(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"2CIMQHirSU0MQqyYHq0eOx\",\n" +
                        "        \"57dN52uHvrHOxijzpIgu3E\",\n" +
                        "        \"1vCWHaC5f2uS3yhpwWbIA6\",\n" +
                        "        \"74ASZWbe4lXaubB36ztrGX\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/following?type=artist");
        res.prettyPrint();
        res.then().statusCode(204);
    }

    @Test(priority = 5)
    public void CheckIfUserFollowsArtistsorUsers(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/following/contains?type=artist&ids=2CIMQHirSU0MQqyYHq0eOx");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test (priority = 4)//3cEYpjA9oz9GiPac4AsH4n
    public void CheckifCurrentUserFollowsPlaylist(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get(" https://api.spotify.com/v1/playlists/"+userplaylistid+"/followers/contains");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test  (priority = 9) //37i9dQZF1DX0XUfTFmNBRM
    public void UnfollowPlaylist(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .delete("https://api.spotify.com/v1/playlists/"+userplaylistid+"/followers");
        res.prettyPrint();
        res.then().statusCode(200);
    }

     @Test
    public void UnfollowArtistsorUsers(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .delete("https://api.spotify.com/v1/playlists/"+userplaylistid+"/followers");
        res.prettyPrint();
         Assert.assertEquals(res.statusCode(),200);
    }



    //---------------------------------------------------Search------------------------------------------------------------

    @Test
    public void SearchforItem(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/search?q=remaster%2520track%3ADoxy%2520artist%3AMiles%2520Davis&type=album");
        res.prettyPrint();
        res.then().statusCode(200);
    }


    //----------------------------------------------------Markets--------------------------------------------------------------

    @Test
    public void GetAvailableMarkets(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/markets");
        res.prettyPrint();
        res.then().statusCode(200);
    }


    //------------------------------------------------------Genre-----------------------------------------------------------

    @Test
    public void GetAvailableGenreSeeds(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/recommendations/available-genre-seeds");
        res.prettyPrint();
        res.then().statusCode(200);
    }


   //------------------------------------------------------Chapter-------------------------------------------------------

    @Test
    public void GetaChapter(){
    Response res = given()
            .header("Accept","*/*")
            .header("Authorization","Bearer " +token)
            .when()
            .get("https://api.spotify.com/v1/chapters/0D5wENdkdwbqlrHoaJ9g29");
    res.prettyPrint();
   // res.then().statusCode(200);
}

    @Test
    public void GetSeveralChapters(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/chapters?ids=0IsXVP0JmcB2adSE338GkK%2C3ZXb8FKZGU0EHALYX6uCzU%2C0D5wENdkdwbqlrHoaJ9g29");
        res.prettyPrint();
       // res.then().statusCode(200);
    }


    //---------------------------------------------Categories------------------------------------------------------------------

    @Test
    public void GetSeveralBrowseCategories(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/browse/categories?locale=IN");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void GetSingleBrowseCategory(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/browse/categories/dinner");
        res.prettyPrint();
        res.then().statusCode(200);
    }


    //------------------------------------------------Artist-------------------------------------------------------------------------------

    @Test(priority = 10)
    @BeforeClass
    public void GetArtist(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg");
        res.prettyPrint();
        artistid = res.path("id");
        System.out.println("ArtistID   " + artistid);
        res.then().statusCode(200);
    }
    
    @Test(priority = 11)
    public void GetSeveralArtists(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/artists?ids=2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 13)
    public void GetArtistsTopTracks(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/artists/"+ artistid +"/top-tracks");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 12)
    public void GetArtistsAlbums(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/artists/"+ artistid +"/albums");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void GetArtistsRelatedArtistsAlbums(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get(" https://api.spotify.com/v1/artists/" +artistid+ "/related-artists");
        res.prettyPrint();
        res.then().statusCode(200);
    }


    //---------------------------------------------------------Albums-----------------------------------------------------------------

    @Test(priority = 14)
    @BeforeClass
    public void GetAlbum(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy");
        res.prettyPrint();
        albumid=res.path("id");
        System.out.println(albumid);
        res.then().statusCode(200);
    }

    @Test(priority = 15)
    public void GetSeveralAlbums(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/albums?ids="+ albumid);
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 16)
    public void GetAlbumTracks(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get(" https://api.spotify.com/v1/albums/"+albumid+"/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 17)
    public void GetUsersSavedAlbums(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/albums");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 18)
    //    1kDZSmw3mKQeAjcmPTLS3M-og
    public void CheckUsersSavedAlbums(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/albums/contains?ids="+albumid);
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void GetNewReleases(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/browse/new-releases");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 19)
    //   1kDZSmw3mKQeAjcmPTLS3M
    public void SaveAlbumsforCurrentUser(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .put("https://api.spotify.com/v1/me/albums?ids="+albumid);
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void RemoveUsersSavedAlbums(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/albums?ids=382ObEPsp2rxGrnsizN5TX%2C1A2GTWGtFfWp7KSQTwWOyo%2C2noRn2Aes5aoNVsU6iWThc");
        res.prettyPrint();
        res.then().statusCode(200);
    }



    //-----------------------------------------------------------Audiobooks-----------------------------------------------------------

    @Test(priority = 20)
    @BeforeClass
    public void GetanAudiobook(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks?ids=18yVqkdbdRvS24c0Ilj2ci");
        res.prettyPrint();
        audiobookid=res.path("id");
        System.out.println(audiobookid);
        res.then().statusCode(200);
    }

    @Test(priority = 21)
    public void GetSeveralAudiobooks(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks?ids="+audiobookid+",1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 24)
    public void GetUsersSavedAudiobooks(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 22)
    public void GetAudiobookChapters(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks?ids="+audiobookid+",1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 23)
    public void CheckUsersSavedAudiobooks(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks?ids="+audiobookid+",1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 25)
    public void SaveAudiobooksforCurrentUser(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .body("{\"ids\": [\"18yVqkdbdRvS24c0Ilj2ci\", \"1HGw3J3NxZO1TP1BTtVhpZ\", \"7iHfbu1YPACw6oZPAFJtqe\"]}")
                .put("https://api.spotify.com/v1/me/audiobooks");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void RemoveUsersSavedAudiobooks(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .delete("https://api.spotify.com/v1/me/audiobooks?ids=18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe");
                 res.prettyPrint();
                 res.then().statusCode(200);
    }



    //-----------------------------------------------------------show------------------------------------------------------

    @Test(priority = 26)
    public void GetSeveralShows(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/shows?ids=5CfCWKI5pZ28U0uOzXkDHe%2C5as3aKmN2k11yfDDDSrvaZ");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test (priority = 27)
    //0sXfyB6o89daCtf4JkH7iP
    @BeforeClass
    public void GetShow(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/shows/5CfCWKI5pZ28U0uOzXkDHe");
        res.prettyPrint();
        showid=res.path("id");
        System.out.println(showid);
        res.then().statusCode(200);
    }

    @Test(priority = 28)
    //6h7u9VphsbDw1m0F2eQBIy
    public void GetShowEpisodes(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/shows/"+showid+"/episodes");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 29)
    public void GetUsersSavedShows(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/shows?ids="+showid+",C5as3aKmN2k11yfDDDSrvaZ");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 30)
    public void SaveShowsforCurrentUser(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .put("https://api.spotify.com/v1/me/shows?ids="+showid);
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test//0sXfyB6o89daCtf4JkH7iP
    public void RemoveUsersSavedShows(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .delete("https://api.spotify.com/v1/me/shows?ids="+showid);
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test(priority = 31)
    public void CheckUsersSavedShows(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/shows/contains?ids="+showid);
        res.prettyPrint();
        res.then().statusCode(200);
    }



    //---------------------------------------------------------tracks-------------------------------------------------------

    @Test
    public void GetTrack(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/tracks/5zCnGtCl5Ac5zlFHXaZmhy");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void GetSeveralTracks(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/tracks?ids=382ObEPsp2rxGrnsizN5TX");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void GetUsersSavedTracks(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void CheckUsersSavedTracks(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/tracks/contains?ids=382ObEPsp2rxGrnsizN5TX");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void SaveTracksforCurrentUser(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .put("https://api.spotify.com/v1/me/tracks?ids=382ObEPsp2rxGrnsizN5TX");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void GetSeveralTracksAudioFeatures(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/audio-features?ids=7ouMYWpwJ422jRcDASZB7P%2C4VqPOruhp5EdPBeR92t6lQ%2C2takcwOaAZWiXQijPHIx7B");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void GetTracksAudioFeatures(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/audio-features/11dFghVXANMlKmJXsNCbNl");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void   GetRecommendations(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/recommendations?seed_artists=4NHQUGzhtTLFvgF5SZesLK&seed_genres=classical%2Ccountry&seed_tracks=0c6xIDDpzE81m2q797ordA");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void   RemoveUsersSavedTracks(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/tracks?ids=7ouMYWpwJ422jRcDASZB7P%2C4VqPOruhp5EdPBeR92t6lQ%2C2takcwOaAZWiXQijPHIx7B");
        res.prettyPrint();
        res.then().statusCode(200);
    }



    //-------------------------------------------------------------playlist---------------------------------------------------------

    @Test
    public void GetPlaylist(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n");
        res.prettyPrint();
        String play=res.path("id");
        System.out.println(play);
        res.then().statusCode(200);
    }

    @Test
    public void ChangePlaylistDetails(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .body("{\n" +
                        "   \n" +
                        "    \"description\": \"Updated playlist description\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/"+playlistid);
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void   GetPlaylistItems(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void   UpdatePlaylistItems(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .body("{\n" +
                        "    \"range_start\": 1,\n" +
                        "    \"insert_before\": 4,\n" +
                        "    \"range_length\": 2\n" +
                        "}\n")
                .when()
                .put("https://api.spotify.com/v1/playlists/"+playlistid+"/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void AddItemstoPlaylist(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .body("{\n" +
                        "    \"uris\": [\n" +
                        "        \"spotify:track:5urYiIXu1ZhfMAOsp7WDTc\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/playlists/"+playlistid+"/tracks");
        res.prettyPrint();
        res.then().statusCode(201);
    }

    @Test
    public void GetCurrentUsersPlaylists(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/playlists");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void   GetUsersPlaylists(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/users/31nakixlkshe2banry5tx3d2hnoe/playlists");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void   CreatePlaylist(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .body("{\n" +
                        "    \"name\": \"New Playlist\",\n" +
                        "    \"description\": \"New playlist description\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/users/31nakixlkshe2banry5tx3d2hnoe/playlists");
        res.prettyPrint();
        res.then().statusCode(201);
    }

    @Test
    public void   GetFeaturedPlaylists(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/browse/featured-playlists");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void   GetCategorysPlaylists(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/browse/categories/dinner/playlists");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void   GetPlaylistCoverImage(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/images");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void  AddCustomPlaylistCoverImage(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .body("/9j/2wCEABoZGSccJz4lJT5CLy8vQkc9Ozs9R0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0cBHCcnMyYzPSYmPUc9Mj1HR0dEREdHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR//dAAQAAf/uAA5BZG9iZQBkwAAAAAH/wAARCAABAAEDACIAAREBAhEB/8QASwABAQAAAAAAAAAAAAAAAAAAAAYBAQAAAAAAAAAAAAAAAAAAAAAQAQAAAAAAAAAAAAAAAAAAAAARAQAAAAAAAAAAAAAAAAAAAAD/2gAMAwAAARECEQA/AJgAH//Z")
                .when()
                .post("https://api.spotify.com/v1/playlists/0WioKBo6Sf6JLGz42Pr4Fv/images");
        res.prettyPrint();
      //  res.then().statusCode(200);
    }


    //-----------------------------------------------------------episodes---------------------------------------------------------------------

    @Test
    @BeforeClass
    //exmaple 3na8lYc2MmdGtCCz5cs4KM    4w2p5chl38Mp35dAubmjzX-og
    public void   GetEpisode(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/episodes/2EtOmHpIHwfRefGGZbutMT");
        res.prettyPrint();
        episodeid=res.path("id");
        System.out.println("True");
        res.then().statusCode(200);
    }

    @Test
    public void   GetSeveralEpisodes(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/episodes?ids="+episodeid);
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void   GetUsersSavedEpisodes(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/episodes");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void  SaveEpisodesforCurrentUser(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .put("https://api.spotify.com/v1/me/episodes?ids="+episodeid);
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void   CheckUsersSavedEpisodes(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/episodes/contains?ids="+episodeid);
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void  RemoveUsersSavedEpisodes(){
        Response res = given()
                .header("Accept","*/*")
                .header("Authorization","Bearer " +token)
                .when()
                .delete("https://api.spotify.com/v1/me/episodes?ids=3na8lYc2MmdGtCCz5cs4KM");
        res.prettyPrint();
        res.then().statusCode(200);
    }



   //---------------------------------------------------------player-----------------------------------------------------------------------

   @Test
   public void  TransferPlayback(){
       Response res = given()
               .header("Accept","*/*")
               .header("Authorization","Bearer " +token)
               .body("{\n" +
                       "    \"device_ids\": [\n" +
                       "        \"74ASZWbe4lXaubB36ztrGX\"\n" +
                       "    ]\n" +
                       "}")
               .when()
               .put("https://api.spotify.com/v1/me/player");
       res.prettyPrint();
       res.then().statusCode(403);
   }

    @Test
    public void   GetCurrentlyPlayingTrack() {
        Response res = given()
                .header("Accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player/currently-playing");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void   GetAvailableDevices() {
        Response res = given()
                .header("Accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player/devices");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void   StartResumePlayback() {
        Response res = given()
                .header("Accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "    \"context_uri\": \"spotify:album:5ht7ItJgpBH7W6vJ5BqpPr\",\n" +
                        "    \"offset\": {\n" +
                        "        \"position\": 5\n" +
                        "    },\n" +
                        "    \"position_ms\": 0\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/player/play");
        res.prettyPrint();
        res.then().statusCode(403);
    }

    @Test
    public void   GettheUsersQueue() {
        Response res = given()
                .header("Accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player/queue");
        res.prettyPrint();
        res.then().statusCode(403);
    }

    @Test
    public void   GetRecentlyPlayedTracks() {
        Response res = given()
                .header("Accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player/recently-played");
        res.prettyPrint();
        res.then().statusCode(200);
    }


}

