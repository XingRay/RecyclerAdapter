# RecyclerAdapter

Adapter for RecyclerView

## How to

To get a Git project into your build:

### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

``` groovy

allprojects {
    repositories {
        ...
            maven { url 'https://jitpack.io' }
        }
    }

```

### Step 2. Add the dependency

[![3.0.0](https://jitpack.io/v/XingRay/RecyclerAdapter.svg)](https://jitpack.io/#XingRay/RecyclerAdapter)

``` groovy

    dependencies {
            implementation 'com.github.XingRay:RecyclerAdapter:3.2.0'
    }

```

### step 3. code

#### 1. one type

##### 1.1 only show data

```kotlin

// init adapter for single layout
adapter = RecyclerAdapter(applicationContext)
            .addType(Data0Layout0ViewHolder::class.java)

// set layoutId
@LayoutId(R.layout.item_data0_layout0)
class Data0Layout0ViewHolder(itemView: View) : ViewHolder<Data0>(itemView) {
}

// loadData
adapter.update(mRepository.loadData())

```

##### 1.2 item click

```kotlin

adapter = RecyclerAdapter(applicationContext)
                .addType(Data0Layout0ViewHolder::class.java, null) { _, position, t ->
                    showToast("position:$position , name:${t.name}")
                }

```

##### 1.3 item select

```kotlin

adapter = RecyclerAdapter(applicationContext)
            .addType(Data0Layout1ViewHolder::class.java, {
               it.selectOnClickListener { t, position ->
                    t.selected = !t.selected
                    adapter?.notifyItemChanged(position, t.selected)
                }
            }, null)

```

#### 2. multi type

##### 2.1 only show data

```kotlin

adapter =  RecyclerAdapter(applicationContext)
            .addType(Data0Layout0ViewHolder::class.java)
            .addType(Data1Layout0ViewHolder::class.java)

```

##### 2.2 item click

```kotlin

adapter =  RecyclerAdapter(applicationContext)
            .addType(Data0Layout0ViewHolder::class.java, null) { _, position, t ->
                showToast("position:$position , name:${t.name}")
            }
            .addType(Data1Layout0ViewHolder::class.java, null) { _, position, t ->
                showToast("position:$position , size:${t.size}")
            }

```

##### 2.3 item select

```kotlin

adapter =  RecyclerAdapter(applicationContext)
            .addType(Data0Layout1ViewHolder::class.java, {
                it.selectOnClickListener { data0, position ->
                    data0.selected = !data0.selected
                    adapter?.notifyItemChanged(position, data0.selected)
                }
            }, null)
            .addType(Data1Layout1ViewHolder::class.java, {
                it.selectOnClickListener { data1, position ->
                    data1.selected = !data1.selected
                    adapter?.notifyItemChanged(position, data1.selected)
                }
            }, null)

```

#### 3. multi type multi layout

##### 3.1 only show data

```kotlin

adapter =  RecyclerAdapter(applicationContext)
            .addViewTypeMapper(Data0::class.java) { _, position -> position % 2 }
            .addLayoutViewSupport(0, Data0Layout0ViewHolder::class.java)
            .addLayoutViewSupport(1, Data0Layout1ViewHolder::class.java)
            .addViewTypeMapper(Data1::class.java) { _, position -> 2 + position % 2 }
            .addLayoutViewSupport(2, Data1Layout0ViewHolder::class.java)
            .addLayoutViewSupport(3, Data1Layout1ViewHolder::class.java)

```

##### 3.2 item click

```kotlin

adapter =  RecyclerAdapter(applicationContext)
                .addViewTypeMapper(Data0::class.java) { _, position -> position % 2 }
                .newLayoutViewSupport(0, Data0Layout0ViewHolder::class.java)
                .itemClickListener { _, position, data0 ->
                    showToast("position:$position, layout:0, name:${data0.name}")
                }.addToAdapter()
                .newLayoutViewSupport(1, Data0Layout1ViewHolder::class.java)
                .itemClickListener { _, position, data0 ->
                    showToast("position:$position,layout:1, name:${data0.name}")
                }
                .addToAdapter()
                .addViewTypeMapper(Data1::class.java) { _, position -> 2 + position % 2 }
                .newLayoutViewSupport(2, Data1Layout0ViewHolder::class.java)
                .itemClickListener { _, position, data1 ->
                    showToast("position:$position, layout:2, size:${data1.size}")
                }
                .addToAdapter()
                .newLayoutViewSupport(3, Data1Layout1ViewHolder::class.java)
                .itemClickListener { _, position, data1 ->
                    showToast("position:$position, layout:3, size:${data1.size}")
                }
                .addToAdapter()

```

##### 3.3 item select

```kotlin

adapter =  RecyclerAdapter(applicationContext)
            .addViewTypeMapper(Data0::class.java) { _, position -> position % 2 }
            .addLayoutViewSupport(0, Data0Layout0ViewHolder::class.java)
            .newLayoutViewSupport(1, Data0Layout1ViewHolder::class.java)
            .initializer {
                it.selectOnClickListener { data0, position ->
                    data0.selected = !data0.selected
                    adapter?.notifyItemChanged(position, data0.selected)
                }
            }
            .addToAdapter()
            .addViewTypeMapper(Data1::class.java) { _, position -> 2 + position % 2 }
            .addLayoutViewSupport(2, Data1Layout0ViewHolder::class.java)
            .newLayoutViewSupport(3, Data1Layout1ViewHolder::class.java)
            .initializer {
                it.selectOnClickListener { data1, position ->
                    data1.selected = !data1.selected
                    adapter?.notifyItemChanged(position, data1.selected)
                }
            }
            .addToAdapter()

```

## more usage see sample
