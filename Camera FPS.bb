Graphics3D 1024,768,32,2

SetBuffer BackBuffer()

light = CreateLight()

Plane = CreatePlane()
PlaneTexture = CreateTexture(128,128,9)
ClsColor 0,200,80
Cls
Color 255,255,255
Rect 0,0,64,64,1
Rect 64,64,64,64,1
CopyRect 0,0,128,128,0,0,BackBuffer(),TextureBuffer(PlaneTexture)
ScaleTexture PlaneTexture,20,20
EntityTexture Plane,PlaneTexture,0,0


Global mouse_y_amount#
Global mouse_x_amount#



camera = CreateCamera()
CameraZoom camera,1.65
PositionEntity camera,0,10,0

Global camera_x_pos# = 0
Global camera_y_pos# = 0
Global camera_z_pos# = 0



Global dir_vec_x# = 0
Global dir_vec_z# = 0

Global dir_vec_x_1# = 0
Global dir_vec_z_1# = 0


Global camera_rotation_x_velocity# = 0
Global camera_rotation_y_velocity# = 0

Global camera_movement_x_velocity# = 0
Global camera_movement_z_velocity# = 0


frameTimer = CreateTimer(60)

Global camera_pitch#





;main loop
While KeyDown(1) = False

WaitTimer(frameTimer) ; limit to 60 fps



;read mouse
mouse_y_amount# = MouseYSpeed()
mouse_x_amount# = MouseXSpeed()

mouse_x_amount# = mouse_x_amount# / 100 ; value of 100 is to scale mouse movemnt.
mouse_y_amount# = mouse_y_amount# / 100
MoveMouse 1024 / 2 , 768 /2 ; move mouse cursor back to the screen centre






forward_speed# = 0
If KeyDown(200) = True Then forward_speed# = 0.1
If KeyDown(208) = True Then forward_speed# = -0.1

;calculate forward movement vector
angle# = EntityYaw#(camera)
s# = Sin(angle#)
c# = Cos(angle#)

dir_vec_x# = 0
dir_vec_z# = 1

dir_vec_x_1# = (dir_vec_x# * c#) + (dir_vec_z# * -s)
dir_vec_z_1# = (dir_vec_x# * s#) + (dir_vec_z# * c)

dir_vec_x_1# = dir_vec_x_1# * forward_speed#
dir_vec_z_1# = dir_vec_z_1# * forward_speed#

camera_movement_x_velocity# = camera_movement_x_velocity# + dir_vec_x_1#
camera_movement_z_velocity# = camera_movement_z_velocity# + dir_vec_z_1#






sideways_speed# = 0
If KeyDown(203) = True Then sideways_speed# = 0.1
If KeyDown(205) = True Then sideways_speed# = -0.1


;calculate strafe movement vector
angle# = EntityYaw#(camera) + 90


s# = Sin(angle#)
c# = Cos(angle#)

dir_vec_x# = 0
dir_vec_z# = 1

dir_vec_x_1# = (dir_vec_x# * c#) + (dir_vec_z# * -s)
dir_vec_z_1# = (dir_vec_x# * s#) + (dir_vec_z# * c)

dir_vec_x_1# = dir_vec_x_1# * sideways_speed#
dir_vec_z_1# = dir_vec_z_1# * sideways_speed#

camera_movement_x_velocity# = camera_movement_x_velocity# + dir_vec_x_1#
camera_movement_z_velocity# = camera_movement_z_velocity# + dir_vec_z_1#




;update smooth camera rotation
camera_rotation_x_velocity# = camera_rotation_x_velocity# + mouse_x_amount#
camera_rotation_y_velocity# = camera_rotation_y_velocity# + mouse_y_amount#

;limit camera pitch
camera_pitch# = EntityPitch#(camera)
If camera_pitch# > 80 Then camera_pitch# = 80
If camera_pitch# < -80 Then camera_pitch# = -80

;update camera orientation by adding camera orientation velocites
RotateEntity camera , camera_pitch# , EntityYaw#(camera) , 0
TurnEntity camera , camera_rotation_y_velocity# , -camera_rotation_x_velocity# , 0


;update cameras position by adding the velocity vectors
camera_x_pos# = camera_x_pos# + camera_movement_x_velocity#
camera_z_pos# = camera_z_pos# + camera_movement_z_velocity#

PositionEntity camera, camera_x_pos# , 10 , camera_z_pos#


;add friction to velocities
camera_rotation_x_velocity# = camera_rotation_x_velocity# * 0.95
camera_rotation_y_velocity# = camera_rotation_y_velocity# * 0.95

camera_movement_x_velocity# = camera_movement_x_velocity# * 0.95
camera_movement_z_velocity# = camera_movement_z_velocity# * 0.95

;update display
RenderWorld
Flip

Wend
;~IDEal Editor Parameters:
;~C#Blitz3D