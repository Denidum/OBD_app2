import mysql.connector

conn = mysql.connector.connect(
    host="DESKTOP-SV03EOB",
    user="boss",
    password="PomeloGranat5",
    database="qdms"
)

cursor = conn.cursor()
