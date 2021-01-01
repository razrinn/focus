package id.ac.ui.cs.mobileprogramming.razrinn.focus

import android.content.Intent
import android.opengl.GLES10
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import javax.microedition.khronos.opengles.GL10

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 5000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val gl = findViewById<GLSurfaceView>(R.id.gl_surface)
        gl.setRenderer(GLRenderer())

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this,MainActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }

    class GLRenderer : GLSurfaceView.Renderer {
        var color = 0f
        var colorVelocity = 1f/60f

        override fun onDrawFrame(gl: GL10){
            if (color > 1 || color < 0){
                colorVelocity = -colorVelocity
            }
            color += colorVelocity

            gl.glClearColor(color * 22f, color, color, 600f)
            gl.glClear(GLES10.GL_COLOR_BUFFER_BIT)
        }

        override fun onSurfaceCreated(p0: GL10?, p1: javax.microedition.khronos.egl.EGLConfig?) {}
        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int){}
    }
}