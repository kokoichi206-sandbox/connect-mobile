package main

import (
	"context"
	"fmt"
	"log"
	"net/http"

	greetv1 "go-server/gen/proto/greet/v1"
	"go-server/gen/proto/greet/v1/greetv1connect"

	"github.com/bufbuild/connect-go"
	"github.com/rs/cors"
	"golang.org/x/net/http2"
	"golang.org/x/net/http2/h2c"
)

type GreetServer struct{}

func (s *GreetServer) Greet(
	ctx context.Context,
	req *connect.Request[greetv1.GreetRequest],
) (*connect.Response[greetv1.GreetResponse], error) {
	log.Println("Request headers: ", req.Header())
	res := connect.NewResponse(&greetv1.GreetResponse{
		Greeting: fmt.Sprintf("Hello, %s!", req.Msg.Name),
	})
	res.Header().Set("Greet-Version", "v1")

	// res.Header().Set("Access-Control-Allow-Origin", "*")
	// res.Header().Set("Access-Control-Allow-Origin", "http://localhost:5173")
	// res.Header().Set("Access-Control-Expose", "*")
	// res.Header().Set("Access-Control-Allow-Headers", "*")
	// res.Header().Set("Access-Control-Allow-Methods", "*")

	return res, nil
}

func main() {
	greeter := &GreetServer{}
	mux := http.NewServeMux()
	path, handler := greetv1connect.NewGreetServiceHandler(greeter)

	c := cors.New(cors.Options{
		AllowedOrigins: []string{"*"},
		// AllowedOrigins: []string{"http://localhost:5173"},
		// https://connect.build/docs/web/common-errors
		AllowedHeaders:   []string{"*"},
		AllowCredentials: true,
		// Enable Debugging for testing, consider disabling in production
		// Debug: true,
	})

	// Insert the middleware
	handler = c.Handler(handler)

	mux.Handle(path, handler)

	http.ListenAndServe(
		// "localhost:9876",
		"0.0.0.0:9876",
		// Use h2c so we can serve HTTP/2 without TLS.
		h2c.NewHandler(mux, &http2.Server{}),
		// handler,
	)
}
