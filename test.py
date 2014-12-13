import serial
import time

ser = serial.Serial(
	port = '/dev/tty.Bluetooth-Incoming-Port',
	baudrate = 19200,
	parity = serial.PARITY_NONE,
	bytesize = serial.EIGHTBITS,
	stopbits = serial.STOPBITS_ONE,
	timeout = None,
	xonxoff = 0,
	rtscts = 0,
	)
# ser.open()
# ser.isOpen()

while 1:
	print ser.readline()