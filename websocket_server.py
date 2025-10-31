import asyncio
import websockets
import json

async def handle_command(websocket, path):
    print("Client connected!")
    try:
        async for message in websocket:
            print(f"Received message: {message}")
            data = json.loads(message)

            # Handle "button_press" command
            if data["command"] == "button_press":
                response = {
                    "status": "success",
                    "message": "Button press acknowledged!"
                }
                await websocket.send(json.dumps(response))
                print(f"Sent response: {response}")

    except Exception as e:
        print(f"Error: {e}")
    finally:
        print("Client disconnected!")

start_server = websockets.serve(handle_command, "0.0.0.0", 8080)

print("WebSocket server started on ws://0.0.0.0:8080")
asyncio.get_event_loop().run_until_complete(start_server)
asyncio.get_event_loop().run_forever()
