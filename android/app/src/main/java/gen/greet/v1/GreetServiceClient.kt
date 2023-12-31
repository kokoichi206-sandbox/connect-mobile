// Code generated by connect-kotlin. DO NOT EDIT.
//
// Source: proto/greet/v1/greet.proto
//
package gen.greet.v1

import build.buf.connect.Headers
import build.buf.connect.MethodSpec
import build.buf.connect.ProtocolClientInterface
import build.buf.connect.ResponseMessage

public class GreetServiceClient(
  private val client: ProtocolClientInterface,
) : GreetServiceClientInterface {
  public override suspend fun greet(request: Greet.GreetRequest, headers: Headers):
      ResponseMessage<Greet.GreetResponse> = client.unary(
    request,
    headers,
    MethodSpec(
    "greet.v1.GreetService/Greet",
      gen.greet.v1.Greet.GreetRequest::class,
      gen.greet.v1.Greet.GreetResponse::class
    ),
  )

}
