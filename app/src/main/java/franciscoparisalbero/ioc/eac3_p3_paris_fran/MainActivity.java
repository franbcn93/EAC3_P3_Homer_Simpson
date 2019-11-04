package franciscoparisalbero.ioc.eac3_p3_paris_fran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imatgeTitol, imatgeTitol_1, imatgeTitol_2, imatgeBlau,
            imatgeVermell, imatgeUll, imatgeDonut, imatgeVerd;
    MediaPlayer mdp;
    Boolean musica= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imatgeTitol = findViewById(R.id.imageTittle);
        imatgeTitol_1 = findViewById(R.id.imageTittle_1);
        imatgeTitol_2 = findViewById(R.id.imageTittle_2);

        imatgeBlau = findViewById(R.id.imageBlue);
        imatgeDonut = findViewById(R.id.imageDonut);
        imatgeVermell = findViewById(R.id.imageRed);
        imatgeUll = findViewById(R.id.imageEyes);
        imatgeVerd = findViewById(R.id.imageGreen);
        mdp = MediaPlayer.create(MainActivity.this,R.raw.the_simpsons);

        //Fem una animació amb les imatges que volem exposar
        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.simpsons_0), 30);
        animation.addFrame(getResources().getDrawable(R.drawable.simpsons_1), 30);
        animation.addFrame(getResources().getDrawable(R.drawable.simpsons_2), 30);
        animation.setOneShot(false);

        ImageView imageAnim =  (ImageView) findViewById(R.id.imageTittle);
        imageAnim.setBackgroundDrawable(animation);

        // Iniciem l'animació
        animation.start();

        //Al clickar al titol, crea un Array i mostra les imatges que falten per mostrar
        imatgeTitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView[] IMGS = { imatgeBlau, imatgeVermell,
                                    imatgeVerd, imatgeDonut, imatgeUll};

                for (int i = 0; i < IMGS.length; i++) {
                    (IMGS[i]).setVisibility(View.VISIBLE);
                }
                rotateImage(IMGS);
            }
        });

        //Al clickar sobre la imatgeDonut començarà a sonar la cançó
        imatgeDonut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mdp.isPlaying() == true)
                    // Pause the music player
                    mdp.pause();
                    // If it's not playing
                else
                    // Resume the music player
                    mdp.start();
            }
        });

    }

    //Si la activitat està parcialment o completament ocult,
    // la música que estava sonant, es para
    @Override
    protected void onPause() {
        super.onPause();
        if(mdp.isPlaying() == true)
            // Pause the music player
            mdp.pause();
            musica=true; //recordarem si estava sonant la música
    }

    //Si tormen a l'activitat principal i la cançó estava sonant, es reanuda
    @Override
    protected void onResume() {
        super.onResume();
        if(musica == true)
            mdp.start();

    }

    //rotateImage agafa l'array de la ImageView i fa l'animació de donar voltes sobre
    // si mateixa de forma indefinida
    private void rotateImage(ImageView[] IMGS){

        //Agafem l'Array del ImageView i no agafem la imatge de l'ull i donut
        for (int i = 0; i < IMGS.length-2; i++) {
            RotateAnimation rotate = new RotateAnimation(0, 360,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(4000);
            rotate.setRepeatCount(Animation.INFINITE);
            (IMGS[i]).startAnimation(rotate);
        }
        rotateDonut();
        rotateImage(imatgeUll);
    }

    // La imatge puja i baixa de dalt a baix de la pantalla
    private void rotateDonut() {

        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation a = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_PARENT,0f,
                Animation.RELATIVE_TO_PARENT, 0.8f);
        a.setDuration(4000);
        a.setRepeatCount(Animation.INFINITE);
        a.setRepeatMode(Animation.REVERSE);

        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(4000);
        rotate.setRepeatCount(Animation.INFINITE);


        animationSet.addAnimation(a);
//        animationSet.addAnimation(rotate);
        imatgeDonut.startAnimation(animationSet);


    }

    //La imatge rota sobre el mateix eix 50 fins a -50 graus, i ho fa d'una
    // manera infinita i reverse
    private void rotateImage(ImageView imageView) {

        RotateAnimation animation = new RotateAnimation(50, -50,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        animation.setDuration(4000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        imageView.startAnimation(animation);

    }
}
