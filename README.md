# SpawnTeleporter
## Плагин, который написан за 2 вечера.

main: me.irubbick.spawnteleporter.SpawnTeleporter  
version: 0.1.14  
name: SpawnTeleporter  
author: iRubbick (Sentinel)  
description: Coffee Dream SpawnTeleporter plugin  

Небольшой мануал по плагину.
1. Основные комманды:
> /spawn<br/>
> /spawn [spawnname]<br/>
> /setspawn<br/>
> /setspawn [name]<br/>
> /spawnlist<br/>
> /relaodspawn<br/>
> /testspawn ater/before/info [name]<br/>
2. Права доступа:
   > ST.admin.notify:  
   
        description: SpawnTeleporter notify  
        default: op  
        
   >  ST.spawn.set:  
   
        description: SetSpawn command
        default: op  
        
   >  ST.spawn.use:  
   
        description: Spawn command
        default: true
   > ST.spawn.customspawn:  
   
        description: Spawn command
        default: op
   > ST.spawn.reload:  
   
        description: ReloadSpawn command
        default: op
   > ST.admin.notify:  
   
        description: SpawnTeleporter notify
        default: op
3. Файл конфигурации.  

> ***locale: ru***  
Локализация сообщений плагина. по умолчанию имеет две локализации - en, ru (messages_en.yml и messages_ru.yml). Плагин всегда будет извлекать имеющиеся внутри себя файлы. Если необходим специальный перевод, необходимо указать это в конфигурации и поместить соответствующий файл в каталог плагина, например messages_de.yml  

> ***use fixed teleport location: false***  
Телепортация игрока только по заданным координатам, без смещения. Смещение применится в случае, если координата застроена либо не безопасна для телепортации (игрок может погибнуть).  

> ***teleportation radius: 10***  
Радиус разброса при телепортации, если отключена фиксация координаты либо локация небезопасна.  

> ***teleport delay (tiks): 100***  
Задержка при телепортации.  

Далее идут настройки эффектов но сейчас их расписывать мне лень -_-, потом в вики распишу.  
***sound effect:***<br/>
            **before teleport:**<br/>
                    use: true<br/>
                    type: ENTITY_SHULKER_TELEPORT <br/>
                    volume: 10<br/>
                    pitch : 1<br/>
            **after teleport:**<br/>
                    use: true<br/>
                    type: ENTITY_GHAST_SHOOT<br/>
                    volume: 10<br/>
                    pitch : 1<br/>
***particle effect:***<br/>
            **before teleport:**<br/>
                    use: true<br/>
                    type: FLAME<br/>
                    count: 50<br/>
                    offset: 0.5<br/>
                    extra: 0<br/>
            **after teleport:**<br/>
                    use: true<br/>
                    type: DRAGON_BREATH<br/>
                    count: 50<br/>
                    offset: 0.5<br/>
                    extra: 0<br/>
***potion effect:***<br/>
            **before teleport:**<br/>
                    use: true<br/>
                    type: CONFUSION<br/>
                    duration (tiks): 200<br/>
                    amplifier: 255<br/>
                    hide particle: true<br/>
            **after teleport:**<br/>
                    use: true<br/>
                    type: SPEED<br/>
                    duration (tiks): 10<br/>
                    amplifier: 10<br/>
                    hide particle: true<br/>
