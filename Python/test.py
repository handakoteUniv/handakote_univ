# always run this code

import serial
import time

# initialize start

input_ser = serial.Serial(
	port = '/dev/tty.Bluetooth-Incoming-Port',
	baudrate = 115200,
	parity = serial.PARITY_NONE,
	bytesize = serial.EIGHTBITS,
	stopbits = serial.STOPBITS_ONE,
	timeout = None,
	xonxoff = 0,
	rtscts = 0,
	)
input_ser.close()
input_ser.open()


output_left_ser = serial.Serial(
	port = '/dev/tty.usbmodemfd121',
	baudrate = 9600,
	parity = serial.PARITY_NONE,
	bytesize = serial.EIGHTBITS,
	stopbits = serial.STOPBITS_ONE,
	timeout = None,
	xonxoff = 0,
	rtscts = 0,
	)
output_left_ser.close()
# lights will be off by this command
output_left_ser.open()

output_right_ser = serial.Serial(
	port = '/dev/tty.usbmodemfa131',
	baudrate = 9600,
	parity = serial.PARITY_NONE,
	bytesize = serial.EIGHTBITS,
	stopbits = serial.STOPBITS_ONE,
	timeout = None,
	xonxoff = 0,
	rtscts = 0,
	)
output_right_ser.close()
# lights will be off by this command
output_right_ser.open()

# initialize over

while 1:
	str = input_ser.readline();
	if "orange" in str:
		print 'orange'
		output_right_ser.write('A\nB\nc\nd\n') # oranges on pink off
		output_left_ser.write('a\nb\nc\nd\n') # left side all off
	elif "pink" in str:
		print 'pink'
		output_right_ser.write('a\nb\nC\nD\n') #orange off pink on
		output_left_ser.write('a\nb\nc\nd\n') # left side all off
	elif "green" in str:
		print 'green'
		output_right_ser.write('a\nb\nc\nd\n') # right side all off
		output_left_ser.write('A\nB\nc\nd\n') # green on blue off
	elif "blue" in str:
		print 'blue'
		output_right_ser.write('a\nb\nc\nd\n') # right side all off
		output_left_ser.write('a\nb\nC\nD\n') # blue on green off
	elif "off" in str:
		print 'off'
		output_right_ser.write('a\nb\nc\nd\n') # right side all off
		output_left_ser.write('a\nb\nc\nd\n') # left side all off
