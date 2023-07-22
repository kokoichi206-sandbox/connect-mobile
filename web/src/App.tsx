import { useState, useEffect } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";

import { ConnectError, createPromiseClient } from "@bufbuild/connect";
import { createConnectTransport } from "@bufbuild/connect-web";

// Import service definition that you want to connect to.
import { GreetService } from "../gen/web/proto/greet/v1/greet_connect";

function App() {
  const [count, setCount] = useState(0);

  // Similar to componentDidMount and componentDidUpdate:
  useEffect(() => {
    // The transport defines what type of endpoint we're hitting.
    // In our example we'll be communicating with a Connect endpoint.
    // If your endpoint only supports gRPC-web, make sure to use
    // `createGrpcWebTransport` instead.
    const transport = createConnectTransport({
      // baseUrl: "http://192.168.0.7:9876",
      baseUrl: "http://localhost:9876/",
    });

    // Here we make the client itself, combining the service
    // definition with the transport.
    const client = createPromiseClient(GreetService, transport);

    try {
      (async () => {
        const res = await client.greet({
          name: "ore",
        });

        console.log(res);
        console.log(res.greeting);
      })();
    } catch (e) {
      console.error(e);

      if (e instanceof ConnectError) {
        console.log(e.code);
        console.log(e.message);
        console.log(e.details);
        console.log(e.cause);
      }
    }
  });

  return (
    <>
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.tsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  );
}

export default App;
