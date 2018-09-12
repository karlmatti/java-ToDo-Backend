import sqlite3
from random import randint
conn = sqlite3.connect('data.sqlite')
c = conn.cursor()
for i in range(100):
    num = randint(1, 100)
    c.execute('insert into numbers values (?)', (num,))

conn.commit()

c.execute("select * from numbers where num >?", (80,))
rows = c.fetchall()
for row in rows:
    print(row)