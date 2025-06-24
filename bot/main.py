import json, telegram, asyncio, threading
from flask import Flask, request

bot = telegram.Bot('8053086599:AAHYNQWmqgA14mOJbDdafLJiUYljOKdKIXY')
loop = asyncio.new_event_loop()
threading.Thread(target=loop.run_forever, daemon=True).start()
app = Flask(__name__)


@app.post('/')
def relay():
    text = (request.get_json(force=True) or {}).get('message', '')
    asyncio.run_coroutine_threadsafe(bot.send_message(368993112, text), loop)
    return '', 204

@app.get("/health")
def health():
    return "OK", 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
