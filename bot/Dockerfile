FROM python:3.11-slim
WORKDIR /app
COPY . /app
RUN pip install --no-cache-dir flask python-telegram-bot
EXPOSE 41401
CMD ["python", "main.py"]
