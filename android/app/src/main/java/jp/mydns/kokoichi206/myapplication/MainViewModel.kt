package jp.mydns.kokoichi206.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import build.buf.connect.ProtocolClientConfig
import build.buf.connect.extensions.GoogleJavaProtobufStrategy
import build.buf.connect.impl.ProtocolClient
import build.buf.connect.okhttp.ConnectOkHttpClient
import build.buf.connect.protocols.NetworkProtocol
import gen.greet.v1.Greet.GreetRequest
import gen.greet.v1.GreetServiceClient
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    init {
        val client = ProtocolClient(
            httpClient = ConnectOkHttpClient(),
            ProtocolClientConfig(
                // ダメだった例:
                // - http://192.168.0.7
                // - http://192.168.0.7:9876
                // - 192.168.0.7
                // - 192.168.0.7:9876
                // Unable to resolve host "192.168.0.7greet.v1.greetservice": No address associated with hostname, とかで気づくべきだったか。。。
                host = "http://192.168.0.7:9876/",
                serializationStrategy = GoogleJavaProtobufStrategy(),
                networkProtocol = NetworkProtocol.CONNECT,
            )
        )

        val greeter = GreetServiceClient(client)
        val req = GreetRequest.newBuilder().setName("it's me").build()
        viewModelScope.launch {
            Log.d("hoge", "client sending request...")
            val resp = greeter.greet(req)

            resp.success { success ->
                Log.d("hoge", "success.message.greeting: ${success.message.greeting}")
            }
            resp.failure { failure ->
                Log.d("hoge", "failure.error: ${failure.error}")
                Log.d("hoge", "failure.code: ${failure.code}")
            }
        }
    }
}