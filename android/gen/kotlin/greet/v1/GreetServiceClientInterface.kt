// Code generated by connect-kotlin. DO NOT EDIT.
//
// Source: proto/greet/v1/greet.proto
//
package greet.v1

import build.buf.connect.Headers
import build.buf.connect.ResponseMessage

public interface GreetServiceClientInterface {
  public suspend fun greet(request: Greet.GreetRequest, headers: Headers = emptyMap()):
      ResponseMessage<Greet.GreetResponse>
}