import wikipedia as w
# #import data.wikipage_pb2 as pb
# import gzip
# from data.models import Page
# from django.conf import settings
# from annoying.functions import get_object_or_None
# import json
# def fetch(keyword, sformat='pb'):
#     # sformat = str(sformat)
#     # #try:
#     # wk = w.page(w.search(keyword)[0])
#     # db_entry = get_object_or_None(Page, title=wk.title)
#     # if db_entry:
#     #     return db_entry.filename.url
#     # else:
#     #     db_entry = Page.objects.create(title=wk.title)
#     # if sformat == 'pb':
#     #     pg = pb.Page()
#     #     pg.id = db_entry.pk
#     #     pg.title = wk.title
#     #     pg.url = wk.url
#     #     pg.page_id = int(wk.pageid)
#     #     pg.revision_id = int(wk.revision_id)
#     #     pg.content = wk.content
#     #     pg.summary = wk.summary
#     #     for i in wk.images:
#     #         img = pg.image.add()
#     #         img.link = i
#     # elif sformat == 'json':
#     #     pg = {}
#     #     pg['id'] = db_entry.pk
#     #     pg['title'] = wk.title
#     #     pg['url'] = wk.url
#     #     pg['page_id'] = int(wk.pageid)
#     #     pg['revision_id'] = int(wk.revision_id)
#     #     pg['content'] = wk.content
#     #     pg['summary'] = wk.summary
#     #     img_data =[]
#     #     for i in wk.images:
#     #         img_data.append(i)
#     #     pg['image'] =img_data
#     # f=gzip.open(settings.MEDIA_ROOT+'/'+str(db_entry.pk)+'.'+sformat+'.gz', 'wb')
#     # if sformat == 'pb':
#     #     f.write(pg.SerializeToString())
#     # elif sformat == 'json':
#     #     f.write(json.dumps(pg))
#     # db_entry.filename = str(db_entry.pk)+'.'+sformat+'.gz'
#     # db_entry.save()
#     # f.close()
#     return '/media/17.json.gz'
#     #except Exception,e:
#     #    return False


import wikipedia as w
import data.wikipage_pb2 as pb
import gzip
from data.models import Page
from django.conf import settings
from annoying.functions import get_object_or_None
import json
def fetch(keyword, sformat='pb'):
    sformat = str(sformat)
    try:
        db_entry = get_object_or_None(Page, title=keyword)
        if db_entry:
            return db_entry.filename.url
        print keyword,sformat
        wksearch = w.search(keyword)
        print wksearch
        wk = w.page(wksearch[0])
        print wk.title
       
        if sformat == 'pb':
            pg = pb.Page()
            pg.id = db_entry.pk
            pg.title = wk.title
            pg.url = wk.url
            pg.page_id = int(wk.pageid)
            pg.revision_id = int(wk.revision_id)
            pg.content = wk.content
            pg.summary = wk.summary
            for i in wk.images:
                img = pg.image.add()
                img.link = i
        elif sformat == 'json':
            pg = {}
            pg['id'] = db_entry.pk
            pg['title'] = wk.title
            pg['url'] = wk.url
            pg['page_id'] = int(wk.pageid)
            pg['revision_id'] = int(wk.revision_id)
            pg['content'] = wk.content
            pg['summary'] = wk.summary
            img_data =[]
            for i in wk.images:
                img_data.append(i)
            pg['image'] =img_data
        f=gzip.open(settings.MEDIA_ROOT+'/'+str(db_entry.pk)+'.'+sformat+'.gz', 'wb')
        if sformat == 'pb':
            f.write(pg.SerializeToString())
        elif sformat == 'json':
            f.write(json.dumps(pg))
        db_entry = Page.objects.create(title=wk.title)
        db_entry.filename = str(db_entry.pk)+'.'+sformat+'.gz'
        db_entry.save()
        f.close()
        return db_entry.filename.url
    except Exception,e:
       return False
