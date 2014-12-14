# always run this code

import serial
import time

# initialize start

input_ser = serial.Serial(
	port = '/dev/tty.Bluetooth-Incoming-Port',
	baudrate = 19200,
	parity = serial.PARITY_NONE,
	bytesize = serial.EIGHTBITS,
	stopbits = serial.STOPBITS_ONE,
	timeout = None,
	xonxoff = 0,
	rtscts = 0,
	)
input_ser.close()
input_ser.open()


output_ser = serial.Serial(
	port = '/dev/tty.usbmodemfd121',
	baudrate = 9600,
	parity = serial.PARITY_NONE,
	bytesize = serial.EIGHTBITS,
	stopbits = serial.STOPBITS_ONE,
	timeout = None,
	xonxoff = 0,
	rtscts = 0,
	)
output_ser.close()
# lights will be off by this command
output_ser.open()

# initialize over

while 1:
	print input_ser.readline()
	# if "yuzu" in input_ser.readline():
	# 	print 'A'
	# 	output_ser.write('A\n')

	# else:
	# 	print "yuzu not found!"
	# 	output_ser.write('a\n')
