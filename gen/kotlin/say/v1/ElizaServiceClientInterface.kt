// Code generated by connect-kotlin. DO NOT EDIT.
//
// Source: proto/say/v1/say.proto
//
package say.v1

import build.buf.connect.Headers
import build.buf.connect.ResponseMessage

public interface ElizaServiceClientInterface {
  public suspend fun say(request: Say.SayRequest, headers: Headers = emptyMap()):
      ResponseMessage<Say.SayResponse>
}
